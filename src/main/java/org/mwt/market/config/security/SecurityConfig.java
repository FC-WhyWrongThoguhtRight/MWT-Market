package org.mwt.market.config.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.mwt.market.config.security.filter.AjaxAuthenticationFilter;
import org.mwt.market.config.security.filter.AjaxAuthenticationFilterConfigurer;
import org.mwt.market.config.security.filter.JwtAuthenticationFilter;
import org.mwt.market.config.security.handler.AjaxAuthenticationFailureHandler;
import org.mwt.market.config.security.handler.AjaxAuthenticationSuccessHandler;
import org.mwt.market.config.security.provider.AjaxAuthenticationProvider;
import org.mwt.market.config.security.provider.JwtProvider;
import org.mwt.market.config.security.service.AjaxUserDetailService;
import org.mwt.market.config.security.service.RefreshTokenService;
import org.mwt.market.config.security.token.AuthenticationDetails;
import org.mwt.market.config.security.token.UserPrincipal;
import org.mwt.market.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //TODO: https로 요청받고 싶은데 swagger에서 http로 요청을 보내서 cors 우회처리가 되지 않는 문제
    @Value("${remote-server.front.url}")
    private String frontUrl;
    @Value("${remote-server.gateway.url}")
    private String gatewayUrl;
    private static final String loginProcUrl = "/login";

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserRepository userRepository;

    private static final String[] AUTH_WHITELIST = {
        "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
        "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/h2-console/**"
    };
    private static final String[] MY_PAGE = {
        "/myInfo", "/myPage", "/myPage/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(request -> true);

        http
            .httpBasic(HttpBasicConfigurer::disable)
            .formLogin(FormLoginConfigurer::disable)
            .cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer
                    .configurationSource(corsConfigurationSource())
            )
            .csrf(CsrfConfigurer::disable)
            .sessionManagement(sessionManagementConfigurer ->
                sessionManagementConfigurer
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
            .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers(
                        Arrays.stream(AUTH_WHITELIST)
                            .map(AntPathRequestMatcher::new)
                            .toArray(AntPathRequestMatcher[]::new)
                    ).permitAll()
                    .requestMatchers(MY_PAGE).authenticated()
                    .anyRequest().permitAll());

        http
            .apply(
                new AjaxAuthenticationFilterConfigurer(new AjaxAuthenticationFilter(loginProcUrl),
                    loginProcUrl))
            .setAuthenticationManager(authenticationManager())
            .successHandlerAjax(
                new AjaxAuthenticationSuccessHandler(jwtProvider, refreshTokenService))
            .failureHandlerAjax(new AjaxAuthenticationFailureHandler())
            .setAuthenticationDetailsSource(authenticationDetailsSource())
            .loginProcessingUrl(loginProcUrl);

        http
            .anonymous((anonymous) ->
                anonymous
                    .principal(new UserPrincipal(null, "anonymous")));

        http
            .addFilterBefore(
                new JwtAuthenticationFilter(authenticationManager(), authenticationDetailsSource()),
                AnonymousAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin(frontUrl);
        corsConfiguration.addAllowedOrigin(gatewayUrl);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new NullSecurityContextRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationDetailsSource<HttpServletRequest,
        WebAuthenticationDetails> authenticationDetailsSource() {
        return context -> new AuthenticationDetails(context);
    }

    @Bean
    public AjaxAuthenticationProvider authenticationProvider(AuthenticationManagerBuilder auth,
        JwtProvider jwtProvider) {
        AjaxAuthenticationProvider ajaxAuthenticationProvider = new AjaxAuthenticationProvider(
            ajaxUserDetailService(), passwordEncoder());
        auth.authenticationProvider(ajaxAuthenticationProvider).authenticationProvider(jwtProvider);
        return ajaxAuthenticationProvider;
    }

    @Bean
    public AjaxUserDetailService ajaxUserDetailService() {
        return new AjaxUserDetailService(userRepository);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
