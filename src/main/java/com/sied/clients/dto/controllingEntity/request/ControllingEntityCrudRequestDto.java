package com.sied.clients.dto.controllingEntity.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ControllingEntityCrudRequestDto {
    @NotNull(message = "{controllingEntity.dto.id_corporate_client.notNull}")
    private Long corporateClient;

    @Size(min = 1, max = 256, message = "{controllingEntity.dto.name.size}")
    @NotBlank(message = "{controllingEntity.dto.name.notBlank}")
    private String name;
}