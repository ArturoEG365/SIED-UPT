package com.sied.clients.dto.jointObligor.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JointObligorCrudRequestDto {
    @NotNull(message = "{jointObligor.dto.id_client.notNull}")
    private Long id_client;

    @NotNull(message = "{jointObligor.dto.id_person.notNull}")
    private Long id_person;

    @NotNull(message = "{jointObligor.dto.id_address.notNull}")
    private Long id_address;

    @NotNull(message = "{jointObligor.dto.id_user.notNull}")
    private Long id_user;
}