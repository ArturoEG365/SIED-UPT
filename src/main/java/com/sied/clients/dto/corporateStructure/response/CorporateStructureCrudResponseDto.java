package com.sied.clients.dto.corporateStructure.response;

import com.sied.clients.entity.corporateClient.CorporateClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CorporateStructureCrudResponseDto {
    private Long id;
    private CorporateClient corporateClient;
    private String name;
    private String position;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
