package com.sied.clients.dto.corporateStructure.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CorporateStructureCrudUpdateRequestDto extends CorporateStructureCrudRequestDto {
    @NotNull(message = "{corporateStructure.dto.id.notNull}")
    private Long id;
}