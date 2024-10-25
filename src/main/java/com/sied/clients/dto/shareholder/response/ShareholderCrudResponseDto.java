package com.sied.clients.dto.shareholder.response;

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

public class ShareholderCrudResponseDto {
    private Long id;
    private CorporateClient corporateClient;
    private String name;
    private Double ownershipPercentage;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}