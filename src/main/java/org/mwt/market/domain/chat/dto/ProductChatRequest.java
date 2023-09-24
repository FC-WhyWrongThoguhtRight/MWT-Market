package org.mwt.market.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductChatRequest {


    @Getter
    @NoArgsConstructor
    public static class ProductChatRequestDto{
        @NotBlank
        @Schema(description = "제품ID")
        private String productId;
    }




}
