package com.sied.clients.dto.address.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressCrudRequestDto {
    @Size(min = 1, max = 256, message = "{address.dto.residenceCountry.size}")
    @NotBlank(message = "{address.dto.residenceCountry.notBlank}")
    private String residenceCountry;

    @Size(min = 1, max = 256, message = "{address.dto.residenceState.size}")
    @NotBlank(message = "{address.dto.residenceState.notBlank}")
    private String residenceState;

    @Size(min = 1, max = 256, message = "{address.dto.street.size}")
    @NotBlank(message = "{address.dto.street.notBlank}")
    private String street;

    @Size(min = 1, max = 256, message = "{address.dto.betweenStreet.size}")
    @NotBlank(message = "{address.dto.betweenStreet.notBlank}")
    private String betweenStreet;

    @Size(min = 1, max = 256, message = "{address.dto.externalNumber.size}")
    @NotBlank(message = "{address.dto.externalNumber.notBlank}")
    private String externalNumber;

    @Size(min = 1, max = 256, message = "{address.dto.internalNumber.size}")
    @NotBlank(message = "{address.dto.internalNumber.notBlank}")
    private String internalNumber;

    @Size(min = 1, max = 256, message = "{address.dto.postalCode.size}")
    @NotBlank(message = "{address.dto.postalCode.notBlank}")
    private String postalCode;

    @Size(min = 1, max = 256, message = "{address.dto.neighborhood.size}")
    @NotBlank(message = "{address.dto.neighborhood.notBlank}")
    private String neighborhood;

    @Size(min = 1, max = 256, message = "{address.dto.municipality.size}")
    @NotBlank(message = "{address.dto.municipality.notBlank}")
    private String municipality;

    @Size(min = 1, max = 256, message = "{address.dto.city.size}")
    @NotBlank(message = "{address.dto.city.notBlank}")
    private String city;

    @Size(min = 1, max = 256, message = "{address.dto.latitude.size}")
    @NotBlank(message = "{address.dto.latitude.notBlank}")
    private String latitude;

    @Size(min = 1, max = 256, message = "{address.dto.longitude.size}")
    @NotBlank(message = "{address.dto.longitude.notBlank}")
    private String longitude;

    @Size(min = 1, max = 256, message = "{address.dto.requestLatitude.size}")
    @NotBlank(message = "{address.dto.requestLatitude.notBlank}")
    private String requestLatitude;

    @Size(min = 1, max = 256, message = "{address.dto.requestLongitude.size}")
    @NotBlank(message = "{address.dto.requestLongitude.notBlank}")
    private String requestLongitude;
}