package com.sied.clients.dto.shareholder.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ShareholderCrudRequestDto {
    @NotNull(message = "{shareholder.dto.id_corporate_client.notNull}")
    private Long id_corporate_client;

    @NotNull(message = "{shareholder.dto.ownershipPercentage.notNull}")
    @DecimalMin(value = "0.0", inclusive = true, message = "{shareholder.dto.ownershipPercentage.min}")
    @DecimalMax(value = "100.0", inclusive = true, message = "{shareholder.dto.ownershipPercentage.max}")
    private Double ownershipPercentage;

    @Size(min = 1, max = 256, message = "{shareholder.dto.name.size}")
    @NotBlank(message = "{shareholder.dto.name.notBlank}")
    private String name;
}