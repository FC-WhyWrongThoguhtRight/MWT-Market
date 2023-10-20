package org.mwt.market.domain.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import java.nio.charset.Charset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mwt.market.domain.product.entity.Product;
import org.mwt.market.domain.product.repository.ProductRepository;
import org.mwt.market.domain.user.dto.UserRequests.LoginRequestDto;
import org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.repository.UserRepository;
import org.mwt.market.domain.wish.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Cookie[] cookies;

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
    public void 상품_목록_조회() throws Exception {
        // given, when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.get("/products")
                .cookie(cookies)
        );

        // then
        String jsonContent = "{\"statusCode\":200,\"message\":\"success\",\"data\":[{\"id\":20,\"title\":\"프로덕트20\",\"price\":10000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"거래완료\",\"likes\":0,\"like\":false},{\"id\":19,\"title\":\"프로덕트19\",\"price\":10000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false},{\"id\":18,\"title\":\"프로덕트18\",\"price\":9000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"예약중\",\"likes\":0,\"like\":false},{\"id\":16,\"title\":\"프로덕트16\",\"price\":7000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false},{\"id\":15,\"title\":\"프로덕트15\",\"price\":6000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"거래완료\",\"likes\":0,\"like\":false},{\"id\":14,\"title\":\"프로덕트14\",\"price\":5000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"예약중\",\"likes\":0,\"like\":false},{\"id\":13,\"title\":\"프로덕트13\",\"price\":4000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false},{\"id\":12,\"title\":\"프로덕트12\",\"price\":3000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":0,\"like\":false},{\"id\":11,\"title\":\"프로덕트11\",\"price\":2000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"거래완료\",\"likes\":0,\"like\":false},{\"id\":10,\"title\":\"프로덕트10\",\"price\":1000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"예약중\",\"likes\":0,\"like\":false}]}";
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
}
