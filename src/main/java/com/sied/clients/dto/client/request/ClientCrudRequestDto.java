package com.sied.clients.dto.client.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientCrudRequestDto {
    @NotNull(message = "{client.dto.id_address.notNull}")
    private Long address;

    @NotNull(message = "{client.dto.id_user.notNull}")
    private Long user;

    @NotNull(message = "{client.dto.id_instance.notNull}")
    private Long instance;

    @NotBlank(message = "{client.dto.client_type.notBlank}")
    @Size(min = 1, max = 50, message = "{client.dto.client_type.size}")
    private String clientType;
}
//client dto
