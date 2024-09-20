package com.sied.clients.entity.address;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "residence_country")
    private String residenceCountry;

    @Column(name = "residence_state")
    private String residenceState;

    @Column(name = "street")
    private String street;

    @Column(name = "between_street")
    private String betweenStreet;

    @Column(name = "external_number")
    private String externalNumber;

    @Column(name = "internal_number")
    private String internalNumber;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "municipality")
    private String municipality;

    @Column(name = "city")
    private String city;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "request_latitude")
    private String requestLatitude;

    @Column(name = "request_longitude")
    private String requestLongitude;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Lifecycle method that is executed before persisting a new entity to the database.
     * Sets the current date and time to the {@code createdAt} and {@code updatedAt} fields.
     * This method ensures that newly created entities have the correct creation and update timestamps.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = true;
    }

    /**
     * Lifecycle method that is executed before updating an existing entity in the database.
     * Sets the current date and time to the {@code updatedAt} field.
     * This method ensures that updated entities reflect the correct timestamp of the last modification.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}