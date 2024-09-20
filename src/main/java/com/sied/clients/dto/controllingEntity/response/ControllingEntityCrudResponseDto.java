package com.sied.clients.dto.controllingEntity.response;

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

public class ControllingEntityCrudResponseDto {
    private Long id;
    private CorporateClient id_corporate_client;
    private String name;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}