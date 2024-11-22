package com.sied.clients.service.corporateClient;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.corporateClient.request.CorporateClientCrudRequestDto;
import com.sied.clients.dto.corporateClient.request.CorporateClientCrudUpdateRequestDto;
import com.sied.clients.dto.corporateClient.response.CorporateClientCrudResponseDto;
import com.sied.clients.entity.client.Client;
import com.sied.clients.entity.corporateClient.CorporateClient;
import com.sied.clients.entity.individualClient.IndividualClient;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.client.ClientRepository;
import com.sied.clients.repository.corporateClient.CorporateClientRepository;
import com.sied.clients.service.client.ClientValidationService;
import com.sied.clients.service.individualClient.IndividualClientValidationService;
import com.sied.clients.util.security.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of {@link CorporateClientCrudService}.
 * Provides CRUD operations for CorporateClient entities.
 */
@Slf4j
@Service
public class CorporateClientCrudServiceImpl implements CorporateClientCrudService {

    private final CorporateClientRepository corporateClientRepository;
    private final ClientRepository clientRepository;
    private final ClientValidationService clientValidationService;
    private final IndividualClientValidationService individualClientValidationService;
    private final MessageSource messageSource;
    private final MessageService messageService;

    private final String entityName = CorporateClient.class.getSimpleName();
    private final String responseDto = CorporateClientCrudResponseDto.class.getSimpleName();
    private final String requestDto = CorporateClientCrudRequestDto.class.getSimpleName();

    /**
     * Constructor for CorporateClientCrudServiceImpl.
     *
     * @param corporateClientRepository the repository for corporate clients
     * @param clientRepository          the repository for clients
     * @param clientValidationService   the service for validating clients
     * @param individualClientValidationService the service for validating individual clients
     * @param messageSource             the message source for internationalization
     * @param messageService            the service for retrieving localized messages
     */
    public CorporateClientCrudServiceImpl(CorporateClientRepository corporateClientRepository,
                                          ClientRepository clientRepository,
                                          ClientValidationService clientValidationService,
                                          IndividualClientValidationService individualClientValidationService,
                                          MessageSource messageSource,
                                          MessageService messageService) {
        this.corporateClientRepository = corporateClientRepository;
        this.clientRepository = clientRepository;
        this.clientValidationService = clientValidationService;
        this.individualClientValidationService = individualClientValidationService;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    /**
     * Creates a new CorporateClient entity.
     *
     * @param request the DTO containing the CorporateClient data
     * @return a CompletableFuture containing the response DTO of the created CorporateClient
     */
    @Async
    @Override
    public CompletableFuture<CorporateClientCrudResponseDto> create(CorporateClientCrudRequestDto request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.debug("Creating {} asynchronously", entityName);
                CorporateClient corporateClient = toEntity(request);
                corporateClient = corporateClientRepository.save(corporateClient);
                return toResponseDto(corporateClient);
            } catch (Exception e) {
                throw handleUnexpectedException("creating", e);
            }
        });
    }

    /**
     * Retrieves all CorporateClient entities in a paginated format.
     *
     * @param offset the starting index for pagination
     * @param limit  the maximum number of items per page
     * @return a CompletableFuture containing the paginated response DTO
     */
    @Async
    @Override
    public CompletableFuture<PaginatedResponse<CorporateClientCrudResponseDto>> getAll(int offset, int limit) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.debug("Retrieving all {}s asynchronously", entityName);
                Page<CorporateClient> corporateClientPage = corporateClientRepository.findAll(PageRequest.of(offset, limit));
                List<CorporateClientCrudResponseDto> corporateClientDtos = corporateClientPage.map(this::toResponseDto).toList();
                return new PaginatedResponse<>(corporateClientPage.getTotalElements(), corporateClientPage.getTotalPages(), corporateClientDtos);
            } catch (Exception e) {
                throw handleUnexpectedException("retrieving", e);
            }
        });
    }

    /**
     * Retrieves a CorporateClient entity by its ID.
     *
     * @param id the ID of the CorporateClient
     * @return a CompletableFuture containing the response DTO of the retrieved CorporateClient
     */
    @Async
    @Override
    public CompletableFuture<CorporateClientCrudResponseDto> get(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.debug("Retrieving {} with ID: {} asynchronously", entityName, id);
                CorporateClient corporateClient = corporateClientRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateClient.service.invalid.corporateClient", new Object[]{id})));
                return toResponseDto(corporateClient);
            } catch (EntityNotFoundException e) {
                log.error("Error retrieving {}: {}", entityName, e.getMessage());
                throw e;
            } catch (Exception e) {
                throw handleUnexpectedException("retrieving", e);
            }
        });
    }

    /**
     * Updates an existing CorporateClient entity.
     *
     * @param request the DTO containing the updated CorporateClient data
     * @return a CompletableFuture containing the response DTO of the updated CorporateClient
     */
    @Async
    @Override
    public CompletableFuture<CorporateClientCrudResponseDto> update(CorporateClientCrudUpdateRequestDto request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.debug("Updating {} with ID: {} asynchronously", entityName, request.getId());
                CorporateClient existingCorporateClient = corporateClientRepository.findById(request.getId())
                        .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateClient.service.invalid.corporateClient", new Object[]{request.getId()})));
                CorporateClient updatedCorporateClient = toEntity(request);
                updatedCorporateClient.setId(existingCorporateClient.getId());
                updatedCorporateClient = corporateClientRepository.save(updatedCorporateClient);
                return toResponseDto(updatedCorporateClient);
            } catch (Exception e) {
                throw handleUnexpectedException("updating", e);
            }
        });
    }

    /**
     * Deletes a CorporateClient entity by its ID.
     *
     * @param id the ID of the CorporateClient
     * @return a CompletableFuture that completes when the deletion is successful
     */
    @Async
    @Override
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
            try {
                log.debug("Deleting {} with ID: {} asynchronously", entityName, id);
                CorporateClient corporateClient = corporateClientRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateClient.service.invalid.corporateClient", new Object[]{id})));
                corporateClientRepository.delete(corporateClient);
            } catch (Exception e) {
                throw handleUnexpectedException("deleting", e);
            }
        });
    }

    /**
     * Maps a request DTO to a CorporateClient entity.
     *
     * @param request the DTO to map
     * @return the mapped CorporateClient entity
     */
    private CorporateClient toEntity(CorporateClientCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);
        Client client = clientValidationService.validateClientExists(request.getClient());
        IndividualClient legalRepresentative = individualClientValidationService.validateIndividualClientExists(request.getLegalRepresentative());
        return CorporateClient.builder()
                .client(client)
                .legalRepresentative(legalRepresentative)
                .subtype(request.getSubtype())
                .name(request.getName())
                .email(request.getEmail())
                .phoneOne(request.getPhoneOne())
                .phoneTwo(request.getPhoneTwo())
                .rfc(request.getRfc())
                .serialNumber(request.getSerialNumber())
                .businessActivity(request.getBusinessActivity())
                .incorporationDate(request.getIncorporationDate())
                .numberOfEmployees(request.getNumberOfEmployees())
                .build();
    }

    /**
     * Maps a CorporateClient entity to a response DTO.
     *
     * @param corporateClient the entity to map
     * @return the mapped response DTO
     */
    private CorporateClientCrudResponseDto toResponseDto(CorporateClient corporateClient) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);
        return CorporateClientCrudResponseDto.builder()
                .id(corporateClient.getId())
                .client(corporateClient.getClient())
                .legalRepresentative(corporateClient.getLegalRepresentative())
                .subtype(corporateClient.getSubtype())
                .name(corporateClient.getName())
                .email(corporateClient.getEmail())
                .phoneOne(corporateClient.getPhoneOne())
                .phoneTwo(corporateClient.getPhoneTwo())
                .rfc(corporateClient.getRfc())
                .serialNumber(corporateClient.getSerialNumber())
                .businessActivity(corporateClient.getBusinessActivity())
                .incorporationDate(corporateClient.getIncorporationDate())
                .numberOfEmployees(corporateClient.getNumberOfEmployees())
                .status(corporateClient.getStatus())
                .createdAt(corporateClient.getCreatedAt())
                .updatedAt(corporateClient.getUpdatedAt())
                .build();
    }

    /**
     * Handles unexpected exceptions during operations.
     *
     * @param action the action being performed when the exception occurred
     * @param e      the exception
     * @return a RuntimeException with a descriptive message
     */
    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}
