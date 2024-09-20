package com.sied.clients.dto.address.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AddressCrudResponseDto {
    private Long id;
    private String residenceCountry;
    private String residenceState;
    private String street;
    private String betweenStreet;
    private String externalNumber;
    private String internalNumber;
    private String postalCode;
    private String neighborhood;
    private String municipality;
    private String city;
    private String latitude;
    private String longitude;
    private String requestLatitude;
    private String requestLongitude;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}