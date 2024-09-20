package com.sied.clients.dto.individualClient.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IndividualClientCrudUpdateRequestDto extends IndividualClientCrudRequestDto {
    @NotNull(message = "{individualClient.dto.id.notNull}")
    private Long id;
}