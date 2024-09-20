package com.sied.clients.dto.corporateClient.response;

import com.sied.clients.entity.client.Client;
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
public class CorporateClientCrudResponseDto {
    private Long id;
    private Client id_client;
    private IndividualClient id_legal_representative;
    private String subtype;
    private String name;
    private String email;
    private String phoneOne;
    private String phoneTwo;
    private String rfc;
    private String serialNumber;
    private String businessActivity;
    private String incorporationDate;
    private String numberOfEmployees;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}