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

@Slf4j
@Service
public class CorporateClientCrudServiceImpl implements CorporateClientCrudService {
    private final CorporateClientRepository corporateClientRepository;
    private final ClientRepository clientRepository;

    private final ClientValidationService clientValidationService;
    private final IndividualClientValidationService individualClientValidationService;

    private final String entityName = CorporateClient.class.getSimpleName();
    private final String responseDto = CorporateClientCrudResponseDto.class.getSimpleName();
    private final String requestDto = CorporateClientCrudRequestDto.class.getSimpleName();
    public final MessageSource messageSource;
    public final MessageService messageService;

    public CorporateClientCrudServiceImpl(CorporateClientRepository corporateClientRepository, ClientRepository clientRepository, ClientValidationService clientValidationService, IndividualClientValidationService individualClientValidationService, MessageSource messageSource, MessageService messageService) {
        this.corporateClientRepository = corporateClientRepository;
        this.clientRepository = clientRepository;
        this.clientValidationService = clientValidationService;
        this.individualClientValidationService = individualClientValidationService;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Async
    @Override
    public CompletableFuture<CorporateClientCrudResponseDto> create(CorporateClientCrudRequestDto request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.debug("Creating {} asynchronously", entityName);
                CorporateClient corporateClient = toEntity(request);
                corporateClient = corporateClientRepository.save(corporateClient);
                return toResponseDto(corporateClient);
            } catch (EntityNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw handleUnexpectedException("creating", e);
            }
        });
    }


    @Async
    @Override
    public CompletableFuture<PaginatedResponse<CorporateClientCrudResponseDto>> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s asynchronously", entityName);
            Page<CorporateClient> corporateClientPage = corporateClientRepository.findAll(PageRequest.of(offset, limit));
            List<CorporateClientCrudResponseDto> corporateClientCrudResponseDtos = corporateClientPage.map(this::toResponseDto).toList();
            PaginatedResponse<CorporateClientCrudResponseDto> response = new PaginatedResponse<>(corporateClientPage.getTotalElements(), corporateClientPage.getTotalPages(), corporateClientCrudResponseDtos);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Async
    @Override
    public CompletableFuture<CorporateClientCrudResponseDto> get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {} asynchronously", entityName, id);
            CorporateClient corporateClient = corporateClientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateClient.service.invalid.corporateClient", new Object[]{id})));
            return CompletableFuture.completedFuture(toResponseDto(corporateClient));
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Async
    @Override
    public CompletableFuture<CorporateClientCrudResponseDto> update(CorporateClientCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {} asynchronously", entityName, request.getId());
            CorporateClient corporateClient = corporateClientRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateClient.service.invalid.corporateClient", new Object[]{request.getId()})));
            CorporateClient updateCorporateClient = toEntity(request);
            updateCorporateClient.setId(corporateClient.getId());
            updateCorporateClient = corporateClientRepository.save(updateCorporateClient);
            return CompletableFuture.completedFuture(toResponseDto(updateCorporateClient));
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("updating", e);
        }
    }

    @Async
    @Override
    public CompletableFuture<Void> delete(Long id) {
        try {
            log.debug("Deleting {} with ID: {} asynchronously", entityName, id);
            CorporateClient corporateClient = corporateClientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateClient.service.invalid.corporateClient", new Object[]{id})));
            corporateClientRepository.delete(corporateClient);
            return CompletableFuture.completedFuture(null);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private CorporateClient toEntity(CorporateClientCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);

        CompletableFuture<Client> clientFuture = CompletableFuture.supplyAsync(() -> clientValidationService.validateClientExists(request.getClient()));
        CompletableFuture<IndividualClient> representativeFuture = CompletableFuture.supplyAsync(() -> individualClientValidationService.validateIndividualClientExists(request.getLegalRepresentative()));

        // Esperamos a que ambas validaciones terminen
        Client client = clientFuture.join();
        IndividualClient legalRepresentative = representativeFuture.join();

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


    private CorporateClient toEntity(CorporateClientCrudUpdateRequestDto request) {
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

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}
