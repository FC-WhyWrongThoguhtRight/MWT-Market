package org.mwt.market.domain.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;

import java.nio.charset.Charset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mwt.market.domain.chat.repository.ChatRoomRepository;
import org.mwt.market.domain.product.dto.ProductStatusUpdateRequestDto;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.repository.ProductRepository;
import org.mwt.market.domain.product.vo.ProductStatus;
import org.mwt.market.domain.user.dto.UserRequests.LoginRequestDto;
import org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureDataMongo
@SpringBootTest(
    properties = {
        "de.flapdoodle.mongodb.embedded.databaseDir=${java.io.tmpdir}/customDir/${random.uuid}"
    }
)
@EnableAutoConfiguration()
@AutoConfigureMockMvc
@Transactional
public class ProductIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Cookie[] cookies;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    private Product product;
    private Long productId;

    @BeforeEach
    void setUp() throws Exception {
        // 회원가입
        SignupRequestDto signupRequestDto = new SignupRequestDto(
                "test@test.com",
                "password",
                "010-0000-0000",
                "awef"
        );
        String content = objectMapper.writeValueAsString(signupRequestDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // 로그인
        LoginRequestDto loginRequestDto = new LoginRequestDto("test@test.com", "password");
        content = objectMapper.writeValueAsString(loginRequestDto);

        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        MockHttpServletResponse response = actions.andReturn().getResponse();

        // 쿠키 받아두기
        this.cookies = response.getCookies();

        // 상품 등록해두기
        ResultActions productAction = mockMvc.perform(MockMvcRequestBuilders
            .multipart("/products")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .param("title", "상품 제목")
            .param("categoryName", "식품")
            .param("content", "내용")
            .param("price", "1000")
            .cookie(cookies)
        );

        this.productId = Long.parseLong(
            productAction.andReturn().getResponse().getContentAsString(Charset.defaultCharset())
                .split("id\":")[1]
                .split(",")[0]
        );

        this.product = productRepository.findById(productId).orElseThrow();
    }

    @Test
    public void 단건_상품_조회() throws Exception {
        // given, when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.get("/products/1")
                .cookie(cookies)
        );

        // then
        String jsonContent = "{\"statusCode\":200,\"message\":\"success\",\"data\":{\"id\":1,\"title\":\"프로덕트1\",\"price\":1000,\"categoryName\":\"식품\",\"content\":\"테스트프로덕트콘텐츠1\",\"images\":[\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"],\"status\":\"판매중\",\"likes\":0,\"seller\":{\"sellerId\":1,\"profileImage\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/default.png\",\"nickname\":\"drkoko\"},\"sellerProductInfos\":[{\"id\":18,\"title\":\"프로덕트18\",\"price\":9000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"},{\"id\":10,\"title\":\"프로덕트10\",\"price\":1000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"},{\"id\":5,\"title\":\"프로덕트5\",\"price\":5000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"},{\"id\":2,\"title\":\"프로덕트2\",\"price\":2000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"}],\"myProduct\":false}}";
        actions.andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    public void 상품_목록_조회() throws Exception {
        // given, when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.get("/products")
                .cookie(cookies)
        );

        // then
        String jsonContent = "{\"statusCode\":200,\"message\":\"success\",\"data\":[{\"id\":21,\"title\":\"상품 제목\",\"price\":1000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false},{\"id\":20,\"title\":\"프로덕트20\",\"price\":10000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"거래완료\",\"likes\":0,\"like\":false},{\"id\":19,\"title\":\"프로덕트19\",\"price\":10000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false},{\"id\":18,\"title\":\"프로덕트18\",\"price\":9000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"예약중\",\"likes\":0,\"like\":false},{\"id\":16,\"title\":\"프로덕트16\",\"price\":7000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false},{\"id\":15,\"title\":\"프로덕트15\",\"price\":6000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"거래완료\",\"likes\":0,\"like\":false},{\"id\":14,\"title\":\"프로덕트14\",\"price\":5000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"예약중\",\"likes\":0,\"like\":false},{\"id\":13,\"title\":\"프로덕트13\",\"price\":4000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false},{\"id\":12,\"title\":\"프로덕트12\",\"price\":3000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false},{\"id\":11,\"title\":\"프로덕트11\",\"price\":2000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"거래완료\",\"likes\":0,\"like\":false}]}";
        actions.andExpect(status().isOk())
            .andExpect(content().json(jsonContent));
    }

    @Test
    public void 카테고리_목록_조회() throws Exception {
        // given, when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.get("/products/categories")
                .cookie(cookies)
        );

        // then
        String jsonContent = "{\"statusCode\":200,\"message\":\"success\",\"data\":[{\"id\":1,\"name\":\"식품\"},{\"id\":2,\"name\":\"의류\"},{\"id\":3,\"name\":\"음료\"}]}";
        actions.andExpect(status().isOk())
            .andExpect(content().json(jsonContent));
    }

    @Test
    public void 판매자의_전체_상품_목록_조회() throws Exception {
        // given, when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.get("/products/1/list")
                .cookie(cookies)
        );

        // then
        String jsonContent = "{\"statusCode\":200,\"message\":\"success\",\"data\":[{\"id\":18,\"title\":\"프로덕트18\",\"price\":9000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"예약중\",\"likes\":0,\"like\":false},{\"id\":10,\"title\":\"프로덕트10\",\"price\":1000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"예약중\",\"likes\":0,\"like\":false},{\"id\":5,\"title\":\"프로덕트5\",\"price\":5000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false},{\"id\":2,\"title\":\"프로덕트2\",\"price\":2000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"예약중\",\"likes\":0,\"like\":false},{\"id\":1,\"title\":\"프로덕트1\",\"price\":1000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false}]}";
        actions.andExpect(status().isOk())
            .andExpect(content().json(jsonContent));
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    public void 상품_등록() throws Exception {
        // given, when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .multipart("/products")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("title", "상품 제목")
                .param("categoryName", "식품")
                .param("content", "내용")
                .param("price", "1000")
                .cookie(cookies)
            );

        // then
        actions.andExpect(status().isOk());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    public void 상품_삭제() throws Exception {
        // given, when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.delete("/products/" + productId)
                .cookie(cookies)
        );


        // then
        actions.andExpect(status().isOk());

        assertThat(product.getDeletedAt()).isNotNull();
        assertThat(product.isDeleted()).isEqualTo(true);
    }

    @Test
    public void 상품_상태_변경() throws Exception {
        // given, when
        ProductStatusUpdateRequestDto requestDto = new ProductStatusUpdateRequestDto(2);
        String content = objectMapper.writeValueAsString(requestDto);

        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.put("/products/" + productId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookies)
                .content(content)
        );


        // then
        actions.andExpect(status().isOk());

        assertThat(product.getStatus()).isEqualTo(ProductStatus.RESERVATION);
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    public void 상품_수정() throws Exception {
        // given, when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
            .put("/products/21")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .param("title", "수정된 상품 제목")
            .param("categoryName", "식품")
            .param("content", "내용")
            .param("price", "1000")
            .cookie(cookies)
        );

        // then
        actions.andExpect(status().isOk());

        Product product = productRepository.findById(productId).orElseThrow();
        assertThat(product.getTitle()).isEqualTo("수정된 상품 제목");
    }

    @Test
    @Disabled
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    public void 상품_채팅방_입장() throws Exception {
        // given, when
        assertThat(chatRoomRepository.findByBuyer_UserIdAndProduct_ProductId(5L, productId).isPresent())
                .isEqualTo(false);

        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/products/" + productId + "/chats")
                        .cookie(cookies)
        );

        // then
        actions.andExpect(status().isOk());
        assertThat(chatRoomRepository.findByBuyer_UserIdAndProduct_ProductId(5L, productId).isPresent())
                .isEqualTo(true);
    }
    @Test
    @Disabled
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    public void 상품_채팅방_목록() throws Exception {
        // given
        mockMvc.perform(
                MockMvcRequestBuilders.post("/products/" + productId + "/chats")
                        .cookie(cookies)
        );
        assertThat(chatRoomRepository.findByBuyer_UserIdAndProduct_ProductId(5L, productId).isPresent())
                .isEqualTo(true);

        // when
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + productId + "/chats")
                        .cookie(cookies)
        );

        // then
        String jsonContent = "{\"statusCode\":200,\"message\":\"success\",\"data\":[{\"chatRoomId\":7,\"productThumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"lastChatMessage\":\"\",\"partnerId\":5,\"partnerNickname\":\"awef\",\"partnerProfileImage\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/default.png\"}]}";
        actions.andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }
}
