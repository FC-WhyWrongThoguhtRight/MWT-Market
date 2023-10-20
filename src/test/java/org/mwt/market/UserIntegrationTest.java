package org.mwt.market;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import jakarta.servlet.http.Cookie;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mwt.market.config.security.provider.JwtProvider;
import org.mwt.market.config.security.token.JwtAuthenticationToken;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.user.dto.UserRequests.LoginRequestDto;
import org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class UserIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    S3Template s3Template;

    @Test
    @DisplayName("[POST 회원_등록] 정상_로직 로직")
    void signup() throws Exception {
        // given
        SignupRequestDto signupRequestDto = signupRequestDto();
        String content = new ObjectMapper().writeValueAsString(signupRequestDto);

        // when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.message").value("회원가입 성공"));

        Optional<User> user = userRepository.findByEmail("test@naver.com");
        Assertions.assertThat(user).isPresent();
    }

    private SignupRequestDto signupRequestDto() {
        SignupRequestDto signupRequestDto = new SignupRequestDto("test@naver.com", "123123",
            "010-4708-6185", "test");
        return signupRequestDto;
    }

    @Test
    @DisplayName("[POST 로그인] 정상_로직")
    void login() throws Exception {
        // given
        LoginRequestDto loginRequestDto = loginRequestDto();
        String content = new ObjectMapper().writeValueAsString(loginRequestDto);

        // when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        // then
        actions.andExpect(status().isOk())
            .andExpect(cookie().exists("access-token"))
            .andExpect(cookie().exists("refresh-token"));
    }

    private LoginRequestDto loginRequestDto() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("donghar@naver.com", "zxcv");
        return loginRequestDto;
    }

    @Test
    @DisplayName("[GET 내_정보_조회] 정상_로직")
    void info() throws Exception {
        // given
        Cookie accessToken = generateCookieForAccessToken();

        // when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.get("/myInfo")
                .cookie(accessToken)
        );

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.data.email").value("donghar@naver.com"))
            .andExpect(jsonPath("$.data.nickname").value("drkoko"))
            .andExpect(jsonPath("$.data.tel").value("010-1234-1234"))
            .andExpect(jsonPath("$.data.profileImage").value(
                "https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/default.png"));
    }

    private Cookie generateCookieForAccessToken() {
        JwtAuthenticationToken authToken = JwtAuthenticationToken.authenticated(
            new UserPrincipal(1L, "donghar@naver.com"), null,
            List.of(new SimpleGrantedAuthority("USER")));
        String accessTokenValue = jwtProvider.generateAccessToken(authToken);
        Cookie accessToken = new Cookie("access-token", accessTokenValue);
        accessToken.setSecure(true);
        accessToken.setHttpOnly(true);
        accessToken.setMaxAge(100000);
        return accessToken;
    }

    @Test
    @DisplayName("[PUT 내_정보_수정] 정상_로직")
    void updateProfile() throws Exception {
        // given
        Cookie accessToken = generateCookieForAccessToken();

        S3Resource mockS3Resource = mock(S3Resource.class);
        when(mockS3Resource.getURL()).thenReturn(
            new URL("https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/1.png"));
        doReturn(mockS3Resource)
            .when(s3Template)
            .upload(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(InputStream.class),
                ArgumentMatchers.any(ObjectMetadata.class));

        // when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.multipart(HttpMethod.PUT,"/myPage/profile")
                .file(new MockMultipartFile("profileImg","testImage.png", "png", new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8))))
                .param("nickname", "newNick")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .cookie(accessToken)
        );

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.email").value("donghar@naver.com"))
            .andExpect(jsonPath("$.data.phone").value("010-1234-1234"))
            .andExpect(jsonPath("$.data.nickname").value("newNick"))
            .andExpect(jsonPath("$.data.profileImg").value(
                "https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/1.png"));
    }

    @Test
    @DisplayName("[GET 내_판매상품_조회] 정상_로직")
    void getMyProduct() throws Exception {
        // given
        Cookie accessToken = generateCookieForAccessToken();
        Integer page = 1;
        Integer pageSize = 10;

        // when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.get("/myPage/products")
                .queryParam("page", String.valueOf(page))
                .queryParam("pageSize", String.valueOf(pageSize))
                .cookie(accessToken)
        );

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("[GET 내_채팅방_조회] 정상_로직")
    void getMyChatRoom() throws Exception {
        // given
        Cookie accessToken = generateCookieForAccessToken();
        Integer page = 1;
        Integer pageSize = 10;

        // when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.get("/myPage/chats")
                .queryParam("page", String.valueOf(page))
                .queryParam("pageSize", String.valueOf(pageSize))
                .cookie(accessToken)
        );

        // then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.data").isArray());
    }
}
