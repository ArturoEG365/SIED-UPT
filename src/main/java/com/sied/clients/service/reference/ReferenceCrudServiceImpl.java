package com.sied.clients.service.reference;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.reference.request.ReferenceCrudUpdateRequestDto;
import com.sied.clients.entity.client.Client;
import com.sied.clients.entity.reference.Reference;
import com.sied.clients.dto.reference.request.ReferenceCrudRequestDto;
import com.sied.clients.dto.reference.response.ReferenceCrudResponseDto;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.client.ClientRepository;
import com.sied.clients.repository.reference.ReferenceRepository;
import com.sied.clients.service.client.ClientValidationService;
import com.sied.clients.util.security.MessageService;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


@Slf4j
@Service
public class ReferenceCrudServiceImpl implements ReferenceCrudService {

    private final ClientRepository clientRepository;
    private final ReferenceRepository referenceRepository;

    private final String entityName = Reference.class.getSimpleName();
    private final String responseDto = ReferenceCrudResponseDto.class.getSimpleName();
    private final String requestDto = ReferenceCrudRequestDto.class.getSimpleName();

    private final MessageSource messageSource;
    private final MessageService messageService;
    private final ClientValidationService clientValidationService;

    public ReferenceCrudServiceImpl(ClientRepository clientRepository, ReferenceRepository referenceRepository, MessageSource messageSource, MessageService messageService, ClientValidationService clientValidationService) {
        this.clientRepository = clientRepository;
        this.referenceRepository = referenceRepository;
        this.messageSource = messageSource;
        this.messageService = messageService;
        this.clientValidationService = clientValidationService;
    }
    
    @Override
    public ReferenceCrudResponseDto create(ReferenceCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);

            Reference reference = toEntity(request);
            reference = referenceRepository.save(reference);

            return toResponseDto(reference);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<ReferenceCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}", entityName);
            Page<Reference> references = referenceRepository.findAll(PageRequest.of(offset, limit));
            List<ReferenceCrudResponseDto> referenceCrudResponseDtos = references.map(this::toResponseDto).getContent();
            return new PaginatedResponse<>(references.getTotalElements() , references.getTotalPages() , referenceCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving all", e);
        }
    }

    @Override
    public ReferenceCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            Reference reference = referenceRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("reference.service.invalid.reference", new Object[]{id})));
            return toResponseDto(reference);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public ReferenceCrudResponseDto update(ReferenceCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            Reference reference = referenceRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("reference.service.invalid.reference", new Object[]{request.getId()})));
            Reference updateReference = toEntity(request);
            updateReference.setId(reference.getId());
            updateReference = referenceRepository.save(updateReference);
            return toResponseDto(updateReference);
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
            Reference reference = referenceRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("reference.service.invalid.reference", new Object[]{id})));
            referenceRepository.delete(reference);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private Reference toEntity(ReferenceCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);
        Client client = clientValidationService.validateClientExists(request.getClient());

        return Reference.builder()
                .client(client)
                .referenceType(request.getReferenceType())
                .name(request.getName())
                .relationship(request.getRelationship())
                .phoneNumber(request.getPhoneNumber())
                .institutionName(request.getInstitutionName())
                .executive(request.getExecutive())
                .contractDate(request.getContractDate())
                .build();
    }

    private ReferenceCrudResponseDto toResponseDto(Reference reference) {
        log.debug("Mapping {} entity to {} DTO", entityName, responseDto);
        return ReferenceCrudResponseDto.builder()
                .id(reference.getId())
                .client(reference.getClient())
                .referenceType(reference.getReferenceType())
                .name(reference.getName())
                .relationship(reference.getRelationship())
                .phoneNumber(reference.getPhoneNumber())
                .institutionName(reference.getInstitutionName())
                .executive(reference.getExecutive())
                .contractDate(reference.getContractDate())
                .status(reference.getStatus())
                .createdAt(reference.getCreatedAt())
                .updatedAt(reference.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException(messageService.getMessage("global.unexpected.error"));
    }
}