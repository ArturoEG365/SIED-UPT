package com.sied.clients.dto.corporateStructure.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CorporateStructureCrudRequestDto {
    @NotNull(message = "{corporateStructure.dto.id_corporate_client.notNull}")
    private Long corporateClient;

    @NotBlank(message = "{corporateStructure.dto.name.NotBlank}")
    @Size(min = 1, max = 100, message = "{corporateStructure.dto.name.size}")
    private String name;

    @NotBlank(message = "{corporateStructure.dto.position.NotBlank}")
    @Size(min = 1, max = 100, message = "{corporateStructure.dto.position.size}")
    private String position;
}