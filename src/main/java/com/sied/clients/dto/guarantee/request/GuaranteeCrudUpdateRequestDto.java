package com.sied.clients.dto.guarantee.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GuaranteeCrudUpdateRequestDto extends GuaranteeCrudRequestDto {
    @NotNull(message = "{guarantee.dto.id.notNull}")
    private Long id;
}