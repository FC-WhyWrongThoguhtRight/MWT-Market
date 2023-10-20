package org.mwt.market.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mwt.market.domain.user.dto.UserRequests.SignupRequestDto;
import org.mwt.market.domain.user.entity.User;
import org.mwt.market.domain.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void signup() throws Exception {
        // given
        SignupRequestDto signupRequestDto = signupRequestDto();
        Mockito.doReturn(false)
            .when(userService)
            .isDuplicated(ArgumentMatchers.any(SignupRequestDto.class));
        Mockito.doReturn(new User("email", "password", "tel", "nickname", null))
            .when(userService)
            .registerUser(ArgumentMatchers.any(SignupRequestDto.class));

        // when
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders.post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signupRequestDto))
        );

        // then
        MvcResult mvcResult = actions.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(responseBody).contains("statusCode", "message");
    }

    private SignupRequestDto signupRequestDto() {
        SignupRequestDto signupRequestDto = new SignupRequestDto();

        return signupRequestDto;
    }

    @Test
    void login() {
    }

    @Test
    void info() {
    }

    @Test
    void updateProfile() {
    }

    @Test
    void getMyProduct() {
    }

    @Test
    void getMyChatRoom() {
    }
}