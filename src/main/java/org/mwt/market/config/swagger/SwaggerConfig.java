package org.mwt.market.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@OpenAPIDefinition(
    servers = {
        @Server(url = "https://api.mwt-market.store/api/v1", description = "Server")
    },
    info = @Info(
        title = "MWT Market API",
        description = "MWT Market Service를 위한 API",
        version = "v1",
        contact = @Contact(name = "고동훤", email = "donghar@naver.com", url = "https://github.com/Dr-KoKo")
    )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        String jwtSchemeName = "access-token";
        String refreshSchemeName = "refresh-token";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName)
            .addList(refreshSchemeName);
        SecurityScheme jwtScheme = new SecurityScheme()
            .name(jwtSchemeName)
            .type(Type.APIKEY)
            .in(In.COOKIE);
        SecurityScheme refreshScheme = new SecurityScheme()
            .name(refreshSchemeName)
            .type(Type.APIKEY)
            .in(In.COOKIE);
        Components components = new Components()
            .addSecuritySchemes(jwtSchemeName, jwtScheme)
            .addSecuritySchemes(refreshSchemeName, refreshScheme);

        return new OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components);
    }
}
