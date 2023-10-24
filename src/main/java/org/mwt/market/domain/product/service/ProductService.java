package org.mwt.market.domain.product.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.chat.dto.ChatRoomDto;
import org.mwt.market.domain.chat.entity.ChatContent;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.chat.repository.ChatContentRepository;
import org.mwt.market.domain.chat.repository.ChatRoomRepository;
import org.mwt.market.domain.product.dto.ProductInfoDto;
import org.mwt.market.domain.product.dto.ProductRequestDto;
import org.mwt.market.domain.product.dto.ProductResponseDto;
import org.mwt.market.domain.product.dto.ProductUpdateRequestDto;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.entity.ProductCategory;
import org.mwt.market.domain.product.entity.ProductImage;
import org.mwt.market.domain.product.exception.ImageTypeException;
import org.mwt.market.domain.product.exception.ImageUploadErrorException;
import org.mwt.market.domain.product.exception.NoPermissionException;
import org.mwt.market.domain.product.exception.NoSuchCategoryException;
import org.mwt.market.domain.product.exception.NoSuchProductException;
import org.mwt.market.domain.product.repository.ProductCategoryRepository;
import org.mwt.market.domain.product.repository.ProductImageRepository;
import org.mwt.market.domain.product.repository.ProductRepository;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.exception.NoSuchUserException;
import org.mwt.market.domain.user.repository.UserRepository;
import org.mwt.market.domain.wish.entity.Wish;
import org.mwt.market.domain.wish.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final S3Template s3Template;
    private final ChatRoomRepository chatRoomRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ChatContentRepository chatContentRepository;

    public List<ProductInfoDto> findAllProducts(List<String> categoryNames, String searchWord,
        Integer page, Integer pageSize, UserPrincipal userPrincipal) {

        Page<Product> products;
        if (categoryNames.size() != 0 && StringUtils.hasText(searchWord)) {
            products = productRepository.findAllByCategory_CategoryNameInTitleContainingOrderByProductIdDesc(
                PageRequest.of(page, pageSize),
                categoryNames, searchWord);
        } else if (categoryNames.size() != 0) {
            products = productRepository.findAllByCategory_CategoryNameInAndIsDeletedFalse(
                PageRequest.of(page, pageSize), categoryNames);
        } else if (StringUtils.hasText(searchWord)) {
            products = productRepository.findAllByTitleContainingAndIsDeletedFalse(
                PageRequest.of(page, pageSize),
                searchWord);
        } else {
            products = productRepository.findAllByIsDeletedFalseOrderByProductIdDesc(
                PageRequest.of(page, pageSize));
        }

        Set<Long> wishProductIds = Collections.emptySet();
        if (!"anonymous".equals(userPrincipal.getName())) {
            User currUser = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new NoSuchUserException());
            List<Wish> findWishProducts = wishRepository.findAllByUserOrderByCreatedAtDesc(
                currUser);
            wishProductIds = findWishProducts.stream()
                .map(wish -> wish.getProduct().getProductId())
                .collect(Collectors.toSet());
        }

        List<ProductInfoDto> result = products.stream()
            .map(ProductInfoDto::toDto)
            .collect(Collectors.toList());
        for (ProductInfoDto productInfoDto : result) {
            productInfoDto.setLike(wishProductIds.contains(productInfoDto.getId()));
        }
        return result;
    }

    @Transactional
    public void deleteProduct(UserPrincipal userPrincipal, Long productId) {
        Product product = validateIsDeleted(productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new));
        if (!userPrincipal.getEmail().equals(product.getSellerEmail())) {
            throw new NoPermissionException();
        }

        product.delete();
    }

    @Transactional
    public void updateProduct(UserPrincipal userPrincipal, Long productId,
        ProductUpdateRequestDto request) {
        Product product = validateIsDeleted(productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new));
        if (!userPrincipal.getEmail().equals(product.getSellerEmail())) {
            throw new NoPermissionException();
        }

        String categoryName = request.getCategoryName();
        ProductCategory productCategory = categoryRepository.findByCategoryName(categoryName)
            .orElseThrow(() -> new NoSuchCategoryException(
                "'" + request.getCategoryName() + "'은 카테고리로 존재하지 않습니다"));

        for (int order = 0; order < product.getProductAlbum().size(); order++) {
            deleteImage(product, order);
        }

        List<ProductImage> productAlbum = new ArrayList<>();
        if (request.getImages() != null) {
            for (int order = 0; order < request.getImages().size(); order++) {
                MultipartFile image = request.getImages().get(order);
                productAlbum.add(uploadImage(product, image, order));
            }
        }

        product.update(request.getTitle(), request.getContent(), productCategory,
            request.getPrice(), productAlbum);
    }

    private void deleteImage(Product product, int order) {
        ProductImage image = product.getProductAlbum().get(order);

        s3Template.deleteObject("mwtmarketbucket",
            "products/" + product.getProductId() + "/" + order);

        productImageRepository.deleteById(image.getImgId());
    }

    @Transactional
    public ProductResponseDto changeStatus(UserPrincipal userPrincipal, Long productId,
        Integer status) {
        Product product = validateIsDeleted(productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new));
        if (!userPrincipal.getEmail().equals(product.getSellerEmail())) {
            throw new NoPermissionException();
        }

        product.changeStatus(status);
        productRepository.save(product);

        return ProductResponseDto.fromEntity(product);
    }

    public List<ChatRoomDto> findChats(UserPrincipal userPrincipal, Long productId) {
        Product product = validateIsDeleted(productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new));

        List<ChatRoom> chatRooms = chatRoomRepository.findAllByProduct(product);
        List<ChatRoomDto> dtos = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            User buyer = chatRoom.getBuyer();
            User you;
            if (userPrincipal.getEmail().equals(buyer.getEmail())) {
                you = product.getSeller();
            } else {
                you = buyer;
            }

            ChatContent lastChatContent = chatContentRepository.findFirstByChatRoomIdOrderByCreateAtDesc(
                chatRoom.getChatRoomId());
            String lastMessage = "";
            LocalDateTime lastCreatedAt = null;
            if (lastChatContent != null) {
                lastMessage = lastChatContent.getContent();
                lastCreatedAt = lastChatContent.getCreateAt();
            }
            ChatRoomDto dto = ChatRoomDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .nickName(you.getNickname())
                .buyerProfileImg(you.getProfileImageUrl())
                .lastMessage(lastMessage)
                .lastCreatedAt(lastCreatedAt)
                .build();
            dtos.add(dto);
        }

        return dtos;
    }

    @Transactional
    public ProductResponseDto addProduct(UserPrincipal userPrincipal, ProductRequestDto request)
        throws ImageTypeException {
        User user = userRepository.findByEmail(userPrincipal.getEmail())
            .orElseThrow(NoSuchUserException::new);
        ProductCategory category = categoryRepository.findByCategoryName(request.getCategoryName())
            .orElseThrow(
                () -> new NoSuchCategoryException(
                    "'" + request.getCategoryName() + "'은 카테고리로 존재하지 않습니다"));

        Product product = Product.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .price(request.getPrice())
            .seller(user)
            .category(category)
            .build();

        Product savedProduct = productRepository.save(product);
        List<ProductImage> productAlbum = new ArrayList<>();

        if (request.getImages() != null) {
            for (int i = 0; i < request.getImages().size(); i++) {
                MultipartFile image = request.getImages().get(i);
                productAlbum.add(uploadImage(savedProduct, image, i));
            }
        }

        savedProduct.setProductAlbum(productAlbum);

        productRepository.save(savedProduct);

        return ProductResponseDto.fromEntity(savedProduct);
    }

    private ProductImage uploadImage(Product product, MultipartFile image, int order) {
        if (image == null || image.isEmpty() || !Objects.requireNonNull(image.getContentType())
            .startsWith("image")) {
            throw new ImageTypeException("올바른 이미지 형식이 아닙니다.");
        }

        String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());

        try {
            S3Resource resource = s3Template.upload("mwtmarketbucket",
                "products/" + product.getProductId() + "/" + order,
                image.getInputStream(),
                ObjectMetadata.builder().contentType(extension).build());

            return productImageRepository.save(
                new ProductImage(product, resource.getURL().toString(), order)
            );
        } catch (IOException ex) {
            throw new ImageUploadErrorException();
        }
    }

    public ProductResponseDto findProduct(UserPrincipal userPrincipal, Long productId) {
        Product product = validateIsDeleted(productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new));
        User user = userRepository.findByEmail(userPrincipal.getEmail())
            .orElseThrow(NoSuchUserException::new);

        List<Product> sellerProducts = productRepository
            .findProductsBySeller_UserId(product.getSeller().getUserId(), productId);

        ProductResponseDto productResponseDto = ProductResponseDto.fromEntity(product,
            sellerProducts);
        productResponseDto.setIsMyProduct(
            Objects.equals(userPrincipal.getId(), product.getSeller().getUserId()));

        List<Wish> findWishProducts = wishRepository.findAllByUserOrderByCreatedAtDesc(user);
        Set<Long> wishProductIds = findWishProducts.stream()
            .map(wish -> wish.getProduct().getProductId())
            .collect(Collectors.toSet());

        productResponseDto.setLike(wishProductIds.contains(productId));

        return productResponseDto;
    }

    private Product validateIsDeleted(Product product) {
        if (product.isDeleted()) {
            throw new NoSuchProductException();
        }
        return product;
    }

    public List<ProductInfoDto> findProductsBySellerId(Integer page, Integer pageSize,
        UserPrincipal userPrincipal, Long productId) {

        Product product = validateIsDeleted(productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new));

        if (!userRepository.existsById(product.getSeller().getUserId())) {
            throw new NoSuchUserException();
        }

        Page<Product> products = productRepository.findProductsBySeller_UserIdAndDeletedFalse(
            PageRequest.of(page - 1, pageSize), product.getSeller().getUserId());

        Set<Long> wishProductIds = Collections.emptySet();
        if (!"anonymous".equals(userPrincipal.getName())) {
            User currUser = userRepository.findById(userPrincipal.getId())
                .orElseThrow(NoSuchUserException::new);
            List<Wish> findWishProducts = wishRepository.findAllByUserOrderByCreatedAtDesc(
                currUser);
            wishProductIds = findWishProducts.stream()
                .map(wish -> wish.getProduct().getProductId())
                .collect(Collectors.toSet());
        }

        List<ProductInfoDto> result = products.stream()
            .map(ProductInfoDto::toDto)
            .collect(Collectors.toList());
        for (ProductInfoDto productInfoDto : result) {
            productInfoDto.setLike(wishProductIds.contains(productInfoDto.getId()));
        }
        return result;
    }

    @Transactional
    public ChatRoomDto joinChatRoom(UserPrincipal userPrincipal, Long productId) {
        Long userId = userPrincipal.getId();

        Optional<ChatRoom> optChatRoom = chatRoomRepository
            .findByBuyer_UserIdAndProduct_ProductId(userId,
                productId);

        ChatRoom chatRoom = optChatRoom.orElseGet(() -> {
            User buyer = userRepository.findById(userId)
                .orElseThrow(NoSuchUserException::new);
            Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchProductException::new);
            return chatRoomRepository.save(ChatRoom.createChatRoom(buyer, product));
        });

        ChatContent lastChatContent = chatContentRepository
            .findFirstByChatRoomIdOrderByCreateAtDesc(chatRoom.getChatRoomId());

        String lastMessage = "";
        if (lastChatContent != null) {
            lastMessage = lastChatContent.getContent();
        }

        return ChatRoomDto.builder()
            .chatRoomId(chatRoom.getChatRoomId())
            .buyerId(chatRoom.getBuyer().getUserId())
            .nickName(chatRoom.getBuyer().getNickname())
            .buyerProfileImg(chatRoom.getBuyer().getProfileImageUrl())
            .lastMessage(lastMessage)
            .lastCreatedAt(chatRoom.getCreatedAt())
            .build();
    }
}
