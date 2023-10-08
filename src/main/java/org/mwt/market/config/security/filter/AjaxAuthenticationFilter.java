package org.mwt.market.config.security.filter;

import static org.mwt.market.domain.user.dto.UserRequests.LoginRequestDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.mwt.market.config.security.token.AjaxAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;


public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AjaxAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {
        //boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        //if (!isAjax) {
        //    throw new IllegalStateException("Authentication is not supported");
        //}
        LoginRequestDto loginRequestDto = objectMapper.readValue(request.getReader(),
            LoginRequestDto.class);
        if (!StringUtils.hasText(loginRequestDto.getEmail()) || !StringUtils.hasText(
            loginRequestDto.getPassword())) {
            throw new IllegalArgumentException("Username Or Password is Empty");
        }
        AjaxAuthenticationToken authRequest = AjaxAuthenticationToken.unauthenticated(
            loginRequestDto.getEmail(), loginRequestDto.getPassword());
        setDetails(request, authRequest);
        return getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, AjaxAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
