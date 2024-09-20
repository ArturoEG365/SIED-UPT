package com.sied.clients.dto.guarantee.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GuaranteeCrudRequestDto {
    @NotNull(message = "{guarantee.dto.id_client.notNull}")
    private Long id_client;

    @Size(min = 1, max = 256, message = "{guarantee.dto.name.size}")
    @NotBlank(message = "{guarantee.dto.name.notBlank}")
    private String name;
}