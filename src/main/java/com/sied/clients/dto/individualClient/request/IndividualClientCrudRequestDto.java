package com.sied.clients.dto.individualClient.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IndividualClientCrudRequestDto {
    @NotNull(message = "{individualClient.dto.id_client.notNull}")
    private Long id_client;

    @NotNull(message = "{individualClient.dto.id_person.notNull}")
    private Long id_person;

    @Size(min = 1, max = 256, message = "{individualClient.dto.subtype.size}")
    @NotBlank(message = "{individualClient.dto.subtype.notBlank}")
    private String subtype;

    @Size(min = 1, max = 256, message = "{individualClient.dto.ocupation.size}")
    @NotBlank(message = "{individualClient.dto.ocupation.notBlank}")
    private String ocupation;

    @NotBlank(message = "{individualClient.dto.maritalStatus.notBlank}")
    private String maritalStatus;
}