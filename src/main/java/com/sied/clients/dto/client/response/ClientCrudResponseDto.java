package com.sied.clients.dto.client.response;

import com.sied.clients.entity.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ClientCrudResponseDto {
    private Long id;
    private Address id_address;
    private Long id_user;
    private Long id_instance;
    private String client_type;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}