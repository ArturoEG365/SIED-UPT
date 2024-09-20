package com.sied.clients.dto.reference.response;

import com.sied.clients.entity.client.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ReferenceCrudResponseDto {
    private Long id;
    private Client id_client;
    private String referenceType;
    private String name;
    private String relationship;
    private String phoneNumber;
    private String institutionName;
    private String executive;
    private LocalDateTime contractDate;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}