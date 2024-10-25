package com.sied.clients.service.individualClient;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.individualClient.request.IndividualClientCrudRequestDto;
import com.sied.clients.dto.individualClient.request.IndividualClientCrudUpdateRequestDto;
import com.sied.clients.dto.individualClient.response.IndividualClientCrudResponseDto;
import com.sied.clients.entity.client.Client;
import com.sied.clients.entity.individualClient.IndividualClient;
import com.sied.clients.entity.person.Person;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.client.ClientRepository;
import com.sied.clients.repository.individualClient.IndividualClientRepository;
import com.sied.clients.repository.person.PersonRepository;
import com.sied.clients.service.client.ClientValidationService;
import com.sied.clients.service.person.PersonValidationService;
import com.sied.clients.util.security.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IndividualClientCrudServiceImpl implements IndividualClientCrudService {
    private  final IndividualClientRepository individualClientRepository;
    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;

    private final PersonValidationService personValidationService;
    private final ClientValidationService clientValidationService;

    private final String entityName = IndividualClient.class.getSimpleName();
    private final String responseDto = IndividualClientCrudResponseDto.class.getSimpleName();
    private final String requestDto = IndividualClientCrudRequestDto.class.getSimpleName();

    public final MessageSource messageSource;
    public final MessageService messageService;

    public IndividualClientCrudServiceImpl(IndividualClientRepository individualClientRepository, ClientRepository clientRepository, PersonRepository personRepository, PersonValidationService personValidationService, ClientValidationService clientValidationService, MessageSource messageSource, MessageService messageService) {
        this.individualClientRepository = individualClientRepository;
        this.clientRepository = clientRepository;
        this.personRepository = personRepository;

        this.personValidationService = personValidationService;
        this.clientValidationService = clientValidationService;

        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Override
    public IndividualClientCrudResponseDto create(IndividualClientCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);
            IndividualClient individualClient = toEntity(request);
            individualClient = individualClientRepository.save(individualClient);
            return toResponseDto(individualClient);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<IndividualClientCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<IndividualClient> individualClient = individualClientRepository.findAll(PageRequest.of(offset, limit));
            List<IndividualClientCrudResponseDto> individualClientCrudResponseDtos = individualClient.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(individualClient.getTotalElements(), individualClient.getTotalPages(), individualClientCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public IndividualClientCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            IndividualClient individualClient = individualClientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("individualClient.service.invalid.individualClient", new Object[]{id})));
            return toResponseDto(individualClient);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public IndividualClientCrudResponseDto update(IndividualClientCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            IndividualClient individualClient = individualClientRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("individualClient.service.invalid.individualClient", new Object[]{request.getId()})));
            IndividualClient updateIndividualClient = toEntity(request);
            updateIndividualClient.setId(individualClient.getId());
            updateIndividualClient = individualClientRepository.save(updateIndividualClient);
            return toResponseDto(updateIndividualClient);
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
            IndividualClient individualClient = individualClientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("individualClient.service.invalid.individualClient", new Object[]{id})));
            individualClientRepository.delete(individualClient);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private IndividualClient toEntity(IndividualClientCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);

        Client client = clientValidationService.validateClientExists(request.getId_client());
        Person person = personValidationService.validatePersonExists(request.getId_person());

        return IndividualClient.builder()
                .client(client)
                .person(person)
                .subtype(request.getSubtype())
                .ocupation(request.getOcupation())
                .maritalStatus(request.getMaritalStatus())
                .build();
    }

    private IndividualClientCrudResponseDto toResponseDto(IndividualClient individualClient) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);

        return IndividualClientCrudResponseDto.builder()
                .id(individualClient.getId())
                .id_client(individualClient.getClient())
                .id_person(individualClient.getPerson())
                .subtype(individualClient.getSubtype())
                .ocupation(individualClient.getOcupation())
                .maritalStatus(individualClient.getMaritalStatus())
                .status(individualClient.getStatus())
                .createdAt(individualClient.getCreatedAt())
                .updatedAt(individualClient.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());

        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}