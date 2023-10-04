package org.mwt.market.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"local", "dev"})
@OpenAPIDefinition(
    info = @Info(
        title = "MWT Market API",
        description = "MWT Market Service를 위한 API",
        version = "v1",
        contact = @Contact(name = "고동훤", email = "donghar@naver.com", url = "https://github.com/Dr-KoKo")
    )
)
@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "Bearer";

    @Bean
    public OpenAPI openApi() {
        String jwtSchemeName = "JwtTokenProvider.AUTHORIZATION_HEADER";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components()
            .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                .name(jwtSchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme(BEARER_TOKEN_PREFIX)
                .bearerFormat("JwtTokenProvider.TYPE"));

        return new OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components);
    }
}
