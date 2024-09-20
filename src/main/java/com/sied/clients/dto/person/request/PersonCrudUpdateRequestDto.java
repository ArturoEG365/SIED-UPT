package com.sied.clients.dto.person.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonCrudUpdateRequestDto extends PersonCrudRequestDto {
    @NotNull(message = "{persons.dto.id.notNull}")
    private Long id;
}
