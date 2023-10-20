package org.mwt.market.domain.wish.controller;

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
public class WishIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Cookie[] cookies;
    private User user;

    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

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

        // User 객체 초기화해두기
        this.user = userRepository.findByEmail("test@test.com").orElseThrow();
    }
    @Test
    // 다른 테스트 케이스에서 추가/삭제한 항목 자체에는 영향받지 않지만
    // 추가/삭제 할 때 PK의 시퀀스가 바뀌는 부분에는 영향받는 부분 때문에 추가하였음
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    public void 관심목록_확인() throws Exception {
        // given
        mockMvc.perform(
            MockMvcRequestBuilders.post("/wish/1")
                .cookie(cookies)
        );

        // when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.get("/wish")
                .cookie(cookies)
        );

        // then
        String jsonContent = "{\"statusCode\":200,\"message\":\"success\",\"data\":[{\"id\":13,\"title\":\"프로덕트1\",\"price\":1000,\"thumbnailImage\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"status\":\"판매중\",\"likes\":1}]}";
        actions.andExpect(status().isOk())
            .andExpect(content().json(jsonContent));
    }

    @Test
    void 관심목록_추가() throws Exception {
        // given, when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.post("/wish/1")
                .cookie(cookies)
        );

        // then
        actions.andExpect(status().isOk());

        Product product = productRepository.findById(1L).orElseThrow();
        assertThat(wishRepository.existsByUserAndProduct(user, product)).isEqualTo(true);
    }

    @Test
    void 관심목록_삭제() throws Exception {
        // given
        mockMvc.perform(
            MockMvcRequestBuilders.post("/wish/1")
                .cookie(cookies)
        );

        Product product = productRepository.findById(1L).orElseThrow();
        assertThat(wishRepository.existsByUserAndProduct(user, product)).isEqualTo(true);

        // when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.delete("/wish/1")
                .cookie(cookies)
        );

        // then
        actions.andExpect(status().isOk());
        assertThat(wishRepository.existsByUserAndProduct(user, product)).isEqualTo(false);
    }
}
