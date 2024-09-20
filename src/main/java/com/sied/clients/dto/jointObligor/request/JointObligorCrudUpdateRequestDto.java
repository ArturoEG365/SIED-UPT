package com.sied.clients.dto.jointObligor.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JointObligorCrudUpdateRequestDto extends JointObligorCrudRequestDto {
    @NotNull(message = "{jointObligor.dto.id.notNull}")
    private Long id;
}
