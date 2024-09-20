package com.sied.clients.dto.boardOfDirector.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardOfDirectorCrudUpdateRequestDto extends BoardOfDirectorCrudRequestDto {
    @NotNull(message = "{boardOfDirector.dto.id.notNull}")
    private Long id;
}