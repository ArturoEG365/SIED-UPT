package com.sied.clients.dto.shareholder.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShareholderCrudUpdateRequestDto extends ShareholderCrudRequestDto {
    @NotNull(message = "{shareholder.dto.id.notNull}")
    private Long id;
}