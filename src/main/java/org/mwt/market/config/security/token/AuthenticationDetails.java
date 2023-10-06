package org.mwt.market.config.security.token;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class AuthenticationDetails extends WebAuthenticationDetails {

    private final String userAgent;

    public AuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.userAgent = request.getHeader("User-Agent");
    }

    public String getUserAgent() {
        return userAgent;
    }
}
