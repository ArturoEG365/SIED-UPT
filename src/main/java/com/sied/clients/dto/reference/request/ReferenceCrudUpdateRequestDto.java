package com.sied.clients.dto.reference.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReferenceCrudUpdateRequestDto extends ReferenceCrudRequestDto {
    @NotNull (message = "{reference.dto.id.notNull}")
    private Long id;
}