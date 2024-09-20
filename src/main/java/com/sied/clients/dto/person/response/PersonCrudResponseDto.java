package com.sied.clients.dto.person.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonCrudResponseDto {
    Long id;
    String name;
    String lastName;
    String maidenName;
    String gender;
    String birthState;
    LocalDate birthDate;
    String email;
    String phoneOne;
    String phoneTwo;
    String rfc;
    String nationality;
    String curp;
}