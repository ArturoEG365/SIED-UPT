package com.sied.clients.dto.controllingEntity.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ControllingEntityCrudUpdateRequestDto extends ControllingEntityCrudRequestDto {
    @NotNull(message = "{controllingEntity.dto.id.notNull}")
    private Long id;
}