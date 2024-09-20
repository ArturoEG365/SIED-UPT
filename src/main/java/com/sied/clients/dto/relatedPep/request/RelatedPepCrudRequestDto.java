package com.sied.clients.dto.relatedPep.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RelatedPepCrudRequestDto {
    @NotNull(message = "{relatedPep.dto.individualClient.notNull}")
    private Long individualClient;

    @Size(min = 1, max = 256, message = "{relatedPep.dto.relationship.size}")
    @NotBlank(message = "{relatedPep.dto.relationship.notBlank}")
    private String relationship;

    @Size(min = 1, max = 256, message = "{relatedPep.dto.position.size}")
    @NotBlank(message = "{relatedPep.dto.position.notBlank}")
    private String position;
}