package com.sied.clients.entity.individualClient;

import com.sied.clients.entity.client.Client;
import com.sied.clients.entity.person.Person;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "individual_clients")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client id_client;

    @ManyToOne
    @JoinColumn(name = "id_person")
    private Person id_person;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "ocupation ")
    private String ocupation ;

    @Column(name = "marital_status ")
    private String maritalStatus ;

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