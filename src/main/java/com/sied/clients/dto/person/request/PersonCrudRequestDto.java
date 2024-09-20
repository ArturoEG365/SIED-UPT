package com.sied.clients.dto.person.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonCrudRequestDto {
    @Size(min = 1, max = 256, message = "{persons.dto.name.size}")
    @NotBlank(message = "{persons.dto.name.notBlank}")
    private String name;

    @Size(min = 1, max = 256, message = "{persons.dto.lastName.size}")
    @NotBlank(message = "{persons.dto.lastName.notBlank}")
    private String lastName;

    @Size(min = 1, max = 256, message = "{persons.dto.maidenName.size}")
    @NotBlank(message = "{persons.dto.maidenName.notBlank}")
    private String maidenName;

    @Size(min = 1, max = 256, message = "{persons.dto.gender.size}")
    @NotBlank(message = "{persons.dto.gender.notBlank}")
    private String gender;

    @Size(min = 1, max = 256, message = "{persons.dto.birthState.size}")
    @NotBlank(message = "{persons.dto.birthState.notBlank}")
    private String birthState;

    @NotNull(message = "{persons.dto.birthDate.notNull}")
    private LocalDate birthDate;

    @Email(message = "{persons.dto.email.email}")
    @NotNull(message = "{persons.dto.email.notNull}")
    private String email;

    @Size(min = 1, max = 12, message = "{persons.dto.phoneOne.size}")
    @NotBlank(message = "{persons.dto.phoneOne.notBlank}")
    private String phoneOne;

    @Size(min = 1, max = 12, message = "{persons.dto.phoneTwo.size}")
    @NotBlank(message = "{persons.dto.phoneTwo.notBlank}")
    private String phoneTwo;

    @Size(min = 1, max = 20, message = "{persons.dto.rfc.size}")
    @NotBlank(message = "{persons.dto.rfc.notBlank}")
    private String rfc;

    @Size(min = 1, max = 256, message = "{persons.dto.nationality.size}")
    @NotBlank(message = "{persons.dto.nationality.notBlank}")
    private String nationality;

    @Size(min = 1, max = 20, message = "{persons.dto.curp.size}")
    @NotBlank(message = "{persons.dto.curp.notBlank}")
    private String curp;
}