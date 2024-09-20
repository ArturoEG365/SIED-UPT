package com.sied.clients.dto.relatedPep.response;

import com.sied.clients.entity.individualClient.IndividualClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatedPepCrudResponseDto {
    private Long id;
    private IndividualClient individualClient;
    private String relationship;
    private String position;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}