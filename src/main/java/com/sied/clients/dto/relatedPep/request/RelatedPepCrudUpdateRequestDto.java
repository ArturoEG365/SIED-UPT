package com.sied.clients.dto.relatedPep.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RelatedPepCrudUpdateRequestDto extends RelatedPepCrudRequestDto {
    @NotNull(message = "{relatedPep.dto.id.notNull}")
    private Long id;
}