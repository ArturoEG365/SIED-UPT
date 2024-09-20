package com.sied.clients.service.person;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.person.request.PersonCrudUpdateRequestDto;
import com.sied.clients.dto.person.request.PersonCrudRequestDto;
import com.sied.clients.dto.person.response.PersonCrudResponseDto;
import com.sied.clients.entity.person.Person;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.person.PersonRepository;
import com.sied.clients.util.security.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PersonCrudServiceImpl implements PersonCrudService{
    private final PersonRepository personRepository;
    private final String entityName = Person.class.getSimpleName();
    private final String responseDto = PersonCrudResponseDto.class.getSimpleName();
    private final String requestDto = PersonCrudRequestDto.class.getSimpleName();
    public final MessageSource messageSource;
    public final MessageService messageService;

    public PersonCrudServiceImpl(PersonRepository personRepository, MessageSource messageSource, MessageService messageService) {
        this.personRepository = personRepository;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Override
    public PersonCrudResponseDto create(PersonCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);

            Person person = toEntity(request);
            person = personRepository.save(person);

            return toResponseDto(person);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<PersonCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<Person> person = personRepository.findAll(PageRequest.of(offset, limit));
            List<PersonCrudResponseDto> personCrudResponseDtos = person.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(person.getTotalElements(), person.getTotalPages(), personCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public PersonCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            Person person = personRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("dto.invalid.user", new Object[]{id})));
            return toResponseDto(person);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public PersonCrudResponseDto update(PersonCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            Person person = personRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("dto.invalid.user", new Object[]{request.getId()})));
            Person updatePerson = toEntity(request);
            updatePerson.setId(person.getId());
            updatePerson = personRepository.save(updatePerson);
            return toResponseDto(updatePerson);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("updating", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            log.debug("Deleting {} with ID: {}", entityName, id);
            Person person = personRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("dto.invalid.user", new Object[]{id})));
            personRepository.delete(person);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private Person toEntity(PersonCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);

        return Person.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .maidenName(request.getMaidenName())
                .gender(request.getGender())
                .birthState(request.getBirthState())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .phoneOne(request.getPhoneOne())
                .phoneTwo(request.getPhoneTwo())
                .rfc(request.getRfc())
                .nationality(request.getNationality())
                .curp(request.getCurp())
                .build();
    }

    private PersonCrudResponseDto toResponseDto(Person person) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);

        return PersonCrudResponseDto.builder()
                .id(person.getId())
                .name(person.getName())
                .lastName(person.getLastName())
                .maidenName(person.getMaidenName())
                .gender(person.getGender())
                .birthState(person.getBirthState())
                .birthDate(person.getBirthDate())
                .email(person.getEmail())
                .phoneOne(person.getPhoneOne())
                .phoneTwo(person.getPhoneTwo())
                .rfc(person.getRfc())
                .nationality(person.getNationality())
                .curp(person.getCurp())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}