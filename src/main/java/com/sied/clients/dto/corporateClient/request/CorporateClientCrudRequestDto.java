package com.sied.clients.dto.corporateClient.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CorporateClientCrudRequestDto {
    @NotNull(message = "{corporateClient.dto.id_client.notNull}")
    private Long id_client;

    @NotNull(message = "{corporateClient.dto.id_legal_representative.notNull}")
    private Long id_legal_representative;

    @Size(min = 1, max = 256, message = "{corporateClient.dto.subtype.size}")
    @NotBlank(message = "{corporateClient.dto.subtype.notBlank}")
    private String subtype;

    @Size(min = 1, max = 256, message = "{corporateClient.dto.name.size}")
    @NotBlank(message = "{corporateClient.dto.name.notBlank}")
    private String name;

    @Email(message = "{corporateClient.dto.email.email}")
    @NotNull(message = "{corporateClient.dto.email.notNull}")
    private String email;

    @Size(min = 1, max = 256, message = "{corporateClient.dto.phoneOne.size}")
    @NotBlank(message = "{corporateClient.dto.phoneOne.notBlank}")
    private String phoneOne;

    @Size(min = 1, max = 256, message = "{corporateClient.dto.phoneTwo.size}")
    @NotBlank(message = "{corporateClient.dto.phoneTwo.notBlank}")
    private String phoneTwo;

    @Size(min = 1, max = 256, message = "{corporateClient.dto.rfc.size}")
    @NotBlank(message = "{corporateClient.dto.rfc.notBlank}")
    private String rfc;

    @Size(min = 1, max = 256, message = "{corporateClient.dto.serialNumber.size}")
    @NotBlank(message = "{corporateClient.dto.serialNumber.notBlank}")
    private String serialNumber;

    @Size(min = 1, max = 256, message = "{corporateClient.dto.businessActivity.size}")
    @NotBlank(message = "{corporateClient.dto.businessActivity.notBlank}")
    private String businessActivity;

    @Size(min = 1, max = 256, message = "{corporateClient.dto.incorporationDate.size}")
    @NotBlank(message = "{corporateClient.dto.incorporationDate.notBlank}")
    private String incorporationDate;

    @Size(min = 1, max = 256, message = "{corporateClient.dto.numberOfEmployees.size}")
    @NotBlank(message = "{corporateClient.dto.numberOfEmployees.notBlank}")
    private String numberOfEmployees;
}