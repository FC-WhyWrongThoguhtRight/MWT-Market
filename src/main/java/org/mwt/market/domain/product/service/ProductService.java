package org.mwt.market.domain.product.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.chat.entity.ChatRoom;
import org.mwt.market.domain.chat.repository.ChatRoomRepository;
import org.mwt.market.domain.product.dto.*;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.entity.ProductCategory;
import org.mwt.market.domain.product.entity.ProductImage;
import org.mwt.market.domain.product.exception.ImageTypeExcpetion;
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

import javax.swing.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final S3Template s3Template;
    private final ChatRoomRepository chatRoomRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    public List<ProductInfoDto> findAllProducts(List<Long> categoryIds, String searchWord,
                                                Integer page, Integer pageSize, UserPrincipal userPrincipal) {

        Page<Product> products;
        if (categoryIds.size() != 0 && StringUtils.hasText(searchWord)) {
            products = productRepository.findAllByCategory_CategoryIdInTitleContainingOrderByProductIdDesc(
                    PageRequest.of(page, pageSize),
                    categoryIds, searchWord);
        } else if (categoryIds.size() != 0) {
            products = productRepository.findAllByCategory_CategoryIdIn(
                    PageRequest.of(page, pageSize), categoryIds);
        } else if (StringUtils.hasText(searchWord)) {
            products = productRepository.findAllByTitleContaining(PageRequest.of(page, pageSize),
                    searchWord);
        } else {
            products = productRepository.findAllByOrderByProductIdDesc(
                    PageRequest.of(page, pageSize));
        }

        Set<Long> wishProductIds = Collections.emptySet();
        if (!"anonymous".equals(userPrincipal.getName())) {
            User currUser = userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new NoSuchUserException());
            List<Wish> findWishProducts = wishRepository.findAllByUser(currUser);
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
        Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchProductException::new);
        if (!userPrincipal.getEmail().equals(product.getSellerEmail())) {
            throw new NoPermissionException();
        }

        product.delete();
    }

    @Transactional
    public void updateProduct(UserPrincipal userPrincipal, Long productId,
                              ProductUpdateRequestDto request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchProductException::new);
        if (!userPrincipal.getEmail().equals(product.getSellerEmail())) {
            throw new NoPermissionException();
        }

        Long categoryId = request.getCategoryId();
        ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                .orElseThrow(NoSuchCategoryException::new);

        for (int order = 0; order < product.getProductAlbum().size(); order++) {
            deleteImage(product, order);
        }

        List<ProductImage> productAlbum = new ArrayList<>();
        for (int order = 0; order < request.getImages().size(); order++) {
            MultipartFile image = request.getImages().get(order);
            productAlbum.add(uploadImage(product, image, order));
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
    public ProductResponseDto changeStatus(UserPrincipal userPrincipal, Long productId, Integer status) {
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
        Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchProductException::new);

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

    @Transactional
    public ProductResponseDto addProduct(UserPrincipal userPrincipal, ProductRequestDto request)
            throws ImageTypeExcpetion {
        User user = userRepository.findByEmail(userPrincipal.getEmail())
                .orElseThrow(NoSuchUserException::new);
        ProductCategory category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                NoSuchElementException::new);

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

                if (image == null || image.isEmpty() || !image.getContentType()
                        .startsWith("image")) {
                    throw new ImageTypeExcpetion("올바른 이미지 형식이 아닙니다.");
                }

                productAlbum.add(uploadImage(savedProduct, image, i));
            }
        }

        savedProduct.setProductAlbum(productAlbum);

        productRepository.save(savedProduct);

        return ProductResponseDto.fromEntity(savedProduct);
    }

    private ProductImage uploadImage(Product product, MultipartFile image, int order) {
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

        List<Product> sellerProducts = productRepository
                .findProductsBySeller_UserId(product.getSeller().getUserId(), productId);

        ProductResponseDto productResponseDto = ProductResponseDto.fromEntity(product, sellerProducts);
        productResponseDto.setIsMyProduct(Objects.equals(userPrincipal.getId(), product.getSeller().getUserId()));

        return productResponseDto;
    }

    private Product validateIsDeleted(Product product) {
        if(product.isDeleted()) {
            throw new NoSuchProductException();
        }
        return product;
    }
}