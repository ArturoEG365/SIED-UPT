package com.sied.clients.dto.boardOfDirector.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BoardOfDirectorCrudRequestDto {
    @NotNull(message = "{boardOfDirector.dto.id_corporate_client.notNull}")
    private Long id_corporate_client;

    @Size(min = 1, max = 256, message = "{boardOfDirector.dto.name.size}")
    @NotBlank(message = "{boardOfDirector.dto.name.notBlank}")
    private String name;

    @Size(min = 1, max = 256, message = "{boardOfDirector.dto.position.size}")
    @NotBlank(message = "{boardOfDirector.dto.position.notBlank}")
    private String position;
}