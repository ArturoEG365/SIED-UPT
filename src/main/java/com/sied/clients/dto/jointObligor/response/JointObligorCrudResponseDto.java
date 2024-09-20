package com.sied.clients.dto.jointObligor.response;

import com.sied.clients.entity.address.Address;
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

public class JointObligorCrudResponseDto {
    private Long id;
    private Client id_client;
    private Person id_person;
    private Address id_address;
    private Long id_user;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}