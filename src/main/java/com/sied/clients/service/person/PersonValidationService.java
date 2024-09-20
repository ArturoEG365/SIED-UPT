package com.sied.clients.service.person;

import com.sied.clients.entity.person.Person;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.person.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonValidationService {
    private final PersonRepository personRepository;

    public PersonValidationService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person validatePersonExists(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person with id " + id + " does not exist."));
    }
}