package com.sied.clients.dto.reference.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReferenceCrudRequestDto {
    @NotNull(message = "{reference.dto.id_client.notNull}")
    private Long client;

    @NotNull(message = "{reference.dto.referenceType}")
    private String referenceType;

    @NotNull(message = "{reference.dto.name.notNull}")
    private String name;

    @NotNull(message = "{reference.dto.relationship.notNull}")
    private String relationship;

    @NotNull(message = "{reference.dto.phoneNumber.notNull}")
    private String phoneNumber;

    @NotNull(message = "{reference.dto.institutionName.notNull}")
    private String institutionName;

    @NotNull(message = "{reference.dto.executive.notNull}")
    private String executive;

    @NotNull(message = "{reference.dto.contractDate.notNull}")
    private LocalDateTime contractDate;
}