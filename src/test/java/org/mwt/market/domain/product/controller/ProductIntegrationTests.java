package org.mwt.market.domain.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductIntegrationTests {

    private int port = 8080;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private final String baseUrl = "http://localhost:" + port;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        // 회원가입
        String signupUrl = baseUrl + "/api/v1/signup";
        ObjectMapper objectMapper = new ObjectMapper();
        String signupRequestBody = objectMapper.writeValueAsString(Map.of(
                "email", "test@test.com",
                "password", "password",
                "phone", "010-0000-0000",
                "nickname", "testNick"
        ));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> signupEntity = new HttpEntity<>(signupRequestBody, headers);
        ResponseEntity<String> signupResponse = restTemplate.postForEntity(signupUrl, signupEntity, String.class);
        assertEquals(HttpStatus.OK, signupResponse.getStatusCode());

        // 로그인
        String loginUrl = baseUrl + "/api/v1/login";
        MultiValueMap<String, String> loginRequest = new LinkedMultiValueMap<>();
        String loginRequestBody = objectMapper.writeValueAsString(Map.of(
                "email", "test@test.com",
                "password", "password"
        ));

        HttpEntity<String> loginEntity = new HttpEntity<>(loginRequestBody, headers);
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(loginUrl, loginEntity, String.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

        // 쿠키 담아두기
        String setCookieHeader = loginResponse.getHeaders().getFirst("Set-Cookie");
        headers.add("Cookie", setCookieHeader);
    }

    @Test
    public void 단건_상품_조회() {
        String productUrl = baseUrl + "/api/v1/products/1";
        ResponseEntity<String> response = restTemplate.exchange(productUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        String responseBody = "{\"statusCode\":200,\"message\":\"success\",\"data\":{\"id\":1,\"title\":\"프로덕트1\",\"price\":1000,\"categoryName\":\"식품\",\"content\":\"테스트프로덕트콘텐츠1\",\"images\":[\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\",\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"],\"status\":\"판매중\",\"likes\":0,\"seller\":{\"sellerId\":1,\"profileImage\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/default.png\",\"nickname\":\"drkoko\"},\"sellerProductInfos\":[{\"id\":18,\"title\":\"프로덕트18\",\"price\":9000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"},{\"id\":10,\"title\":\"프로덕트10\",\"price\":1000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"},{\"id\":5,\"title\":\"프로덕트5\",\"price\":5000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"},{\"id\":2,\"title\":\"프로덕트2\",\"price\":2000,\"thumbnail\":\"https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png\"}],\"myProduct\":false}}";
        System.out.println("@@@" + response.getBody());
        assertEquals(responseBody, response.getBody());
    }
}
