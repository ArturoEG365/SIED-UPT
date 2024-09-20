package com.sied.clients.dto.individualClient.response;

import com.sied.clients.entity.client.Client;
import com.sied.clients.entity.person.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class IndividualClientCrudResponseDto {
    private Long id;
    private Client id_client;
    private Person id_person;
    private String subtype;
    private String ocupation ;
    private String maritalStatus;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}