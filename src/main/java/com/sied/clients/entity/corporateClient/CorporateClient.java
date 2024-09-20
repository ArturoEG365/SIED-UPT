package com.sied.clients.entity.corporateClient;

import com.sied.clients.entity.client.Client;
import com.sied.clients.entity.individualClient.IndividualClient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "corporate_clients")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CorporateClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client id_client;

    @ManyToOne
    @JoinColumn(name = "id_legal_representative")
    private IndividualClient id_legal_representative;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_one")
    private String phoneOne;

    @Column(name = "phone_two")
    private String phoneTwo;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "business_activity")
    private String businessActivity;

    @Column(name = "incorporation_date")
    private String incorporationDate;

    @Column(name = "number_of_employees")
    private String numberOfEmployees;

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