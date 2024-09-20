package com.sied.clients.dto.corporateClient.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CorporateClientCrudUpdateRequestDto extends CorporateClientCrudRequestDto {
    @NotNull(message = "{corporateClient.dto.id.notNull}")
    private Long id;
}