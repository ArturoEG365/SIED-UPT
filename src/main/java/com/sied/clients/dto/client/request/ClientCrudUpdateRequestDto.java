package com.sied.clients.dto.client.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientCrudUpdateRequestDto extends ClientCrudRequestDto {
    @NotNull(message = "{client.dto.id.notNull}")
    private Long id;
}
