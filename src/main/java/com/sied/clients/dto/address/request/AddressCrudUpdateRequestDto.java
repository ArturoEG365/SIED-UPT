package com.sied.clients.dto.address.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressCrudUpdateRequestDto extends AddressCrudRequestDto {
    @NotNull(message = "{address.dto.id.notNull}")
    private Long id;
}
