package org.mwt.market.domain.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mwt.market.domain.user.dto.UserRequests.LoginRequestDto;
import org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
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
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.get("/products/1")
                .cookie(cookies)
        );

        String jsonContent = "{\"statusCode\":200,\"message\":\"success\",\"data\":{\"id\":1,\"title\":\"프로덕트1\",\"price\":1000,\"categoryName\":\"식품\",\"content\":\"테스트프로덕트콘텐츠1\",\"images\":[\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"],\"status\":\"판매중\",\"likes\":0,\"seller\":{\"sellerId\":1,\"profileImage\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/default.png\",\"nickname\":\"drkoko\"},\"sellerProductInfos\":[{\"id\":18,\"title\":\"프로덕트18\",\"price\":9000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"},{\"id\":10,\"title\":\"프로덕트10\",\"price\":1000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"},{\"id\":5,\"title\":\"프로덕트5\",\"price\":5000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"},{\"id\":2,\"title\":\"프로덕트2\",\"price\":2000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"}],\"myProduct\":false}}";
        actions.andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }
}
