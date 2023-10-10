package org.mwt.market.domain.product.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.chat.repository.ChatRoomRepository;
import org.mwt.market.domain.product.dto.ProductChatResponseDto;
import org.mwt.market.domain.product.dto.ProductInfoDto;
import org.mwt.market.domain.product.dto.ProductRequestDto;
import org.mwt.market.domain.product.dto.ProductResponseDto;
import org.mwt.market.domain.product.dto.ProductSearchRequestDto;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.entity.ProductCategory;
import org.mwt.market.domain.product.entity.ProductImage;
import org.mwt.market.domain.product.exception.ImageUploadErrorException;
import org.mwt.market.domain.product.exception.NoPermissionException;
import org.mwt.market.domain.product.exception.NoSuchProductException;
import org.mwt.market.domain.product.repository.ProductCategoryRepository;
import org.mwt.market.domain.product.repository.ProductRepository;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.exception.NoSuchUserException;
import org.mwt.market.domain.user.repository.UserRepository;
import org.mwt.market.domain.wish.entity.Wish;
import org.mwt.market.domain.wish.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
    private final ChatRoomRepository chatRoomRepository;
    private final ProductCategoryRepository categoryRepository;

    private final S3Template s3Template;

    public List<ProductInfoDto> findAllProducts(ProductSearchRequestDto request, UserPrincipal userPrincipal) {

        List<Integer> categoryIds = request.getCategoryIds();
        String searchWord = request.getSearchWord();
        Integer pageSize = request.getPageSize();
        Integer page = request.getPage();

        Page<Product> products = productRepository.findAll(PageRequest.of(page, pageSize, Sort.sort(Product.class).descending()));

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(NoSuchUserException::new);
        List<Wish> findWishProducts = wishRepository.findAllByUser(user);
        Set<Long> wishProductIds = findWishProducts.stream()
                .map(wish -> wish.getProduct().getId())
                .collect(Collectors.toSet());

        List<ProductInfoDto> productInfos = products.stream()
                .map(ProductInfoDto::toDto)
                .collect(Collectors.toList());

        return productInfos;
    }

    @Transactional
    public ProductResponseDto changeStatus(UserPrincipal userPrincipal, Long productId, String status) {
        Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchProductException::new);
        if (!userPrincipal.getEmail().equals(product.getSellerEmail())) {
            throw new NoPermissionException();
        }

        product.changeStatus(status);
        productRepository.save(product);

        return ProductResponseDto.fromEntity(product);
    }

    public List<ProductChatResponseDto> findChats(UserPrincipal userPrincipal, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(NoSuchProductException::new);

        List<ChatRoom> chatRooms = chatRoomRepository.findAllByProduct(product);
        List<ProductChatResponseDto> dtos = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            User buyer = chatRoom.getBuyer();
            User you;
            if (userPrincipal.getEmail().equals(buyer.getEmail())) {
                you = product.getSeller();
            } else {
                you = buyer;
            }

            ProductChatResponseDto dto = ProductChatResponseDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .productThumbnail(product.getThumbnail())
                .youId(you.getUserId())
                .youNickname(you.getNickname())
                .youProfileImage(you.getProfileImageUrl())
                .lastChatMessage("미구현") // TODO 종훈님 구현완료 후 수정
                .build();
            dtos.add(dto);
        }

        return dtos;
    }

    public ProductResponseDto addProduct(UserPrincipal userPrincipal, ProductRequestDto request) {
        User user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(NoSuchUserException::new);
        ProductCategory category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
            NoSuchElementException::new);

        Product product = Product.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .price(request.getPrice())
            .seller(user)
            .productCategory(category)
            .build();

        Product savedProduct = productRepository.save(product);

        List<ProductImage> productAlbum = new ArrayList<>();
        for (int i = 0; i < request.getImages().size(); i++) {
            MultipartFile image = request.getImages().get(i);
            productAlbum.add(uploadImage(savedProduct, image, i));
        }

        savedProduct.setProductAlbum(productAlbum);
        productRepository.save(savedProduct);

        return ProductResponseDto.fromEntity(savedProduct);
    }

    private ProductImage uploadImage(Product product, MultipartFile image, int order) {
        String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());

        try {
            S3Resource resource = s3Template.upload("mwtmarketbucket",
                "products/" + product.getId() + "/" + System.currentTimeMillis() + "/" + order,
                image.getInputStream(),
                ObjectMetadata.builder().contentType(extension).build());

            return new ProductImage(product, resource.getURL().toString(), order);
        } catch (IOException ex) {
            throw new ImageUploadErrorException();
        }
    }
}
