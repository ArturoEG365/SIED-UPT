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
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CorporateClientCrudServiceImpl implements CorporateClientCrudService{
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

    @Override
    public CorporateClientCrudResponseDto create(CorporateClientCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);
            CorporateClient corporateClient = toEntity(request);
            corporateClient = corporateClientRepository.save(corporateClient);
            return toResponseDto(corporateClient);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<CorporateClientCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<CorporateClient> corporateClient = corporateClientRepository.findAll(PageRequest.of(offset, limit));
            List<CorporateClientCrudResponseDto> individualClientCrudResponseDtos = corporateClient.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(corporateClient.getTotalElements(), corporateClient.getTotalPages(), individualClientCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public CorporateClientCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            CorporateClient corporateClient = corporateClientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateClient.service.invalid.corporateClient", new Object[]{id})));
            return toResponseDto(corporateClient);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public CorporateClientCrudResponseDto update(CorporateClientCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            CorporateClient corporateClient = corporateClientRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateClient.service.invalid.corporateClient", new Object[]{request.getId()})));
            CorporateClient updateCorporateClient = toEntity(request);
            updateCorporateClient.setId(corporateClient.getId());
            updateCorporateClient = corporateClientRepository.save(updateCorporateClient);
            return toResponseDto(updateCorporateClient);
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
            CorporateClient corporateClient = corporateClientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateClient.service.invalid.corporateClient", new Object[]{id})));
            corporateClientRepository.delete(corporateClient);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private CorporateClient toEntity(CorporateClientCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);

        Client client = clientValidationService.validateClientExists(request.getId_client());
        IndividualClient legalRepresentative = individualClientValidationService.validateIndividualClientExists(request.getId_legal_representative());

        return CorporateClient.builder()
                .id_client(client)
                .id_legal_representative(legalRepresentative)
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
                .id_client(corporateClient.getId_client())
                .id_legal_representative(corporateClient.getId_legal_representative())
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