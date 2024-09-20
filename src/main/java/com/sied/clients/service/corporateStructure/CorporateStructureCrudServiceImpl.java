package com.sied.clients.service.corporateStructure;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.corporateStructure.response.CorporateStructureCrudResponseDto;
import com.sied.clients.entity.corporateClient.CorporateClient;
import com.sied.clients.entity.corporateStructure.CorporateStructure;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.corporateStructure.CorporateStructureRepository;
import com.sied.clients.dto.corporateStructure.request.CorporateStructureCrudRequestDto;
import com.sied.clients.dto.corporateStructure.request.CorporateStructureCrudUpdateRequestDto;
import com.sied.clients.service.corporateClient.CorporateClientValidationService;
import com.sied.clients.util.security.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CorporateStructureCrudServiceImpl implements CorporateStructureCrudService {
    private final CorporateStructureRepository corporateStructureRepository;

    private final String entityName = CorporateStructure.class.getSimpleName();
    private final String responseDto = CorporateStructureCrudResponseDto.class.getSimpleName();
    private final String requestDto = CorporateStructureCrudRequestDto.class.getSimpleName();

    private final CorporateClientValidationService corporateClientValidationService;

    public final MessageSource messageSource;
    public final MessageService messageService;

    public CorporateStructureCrudServiceImpl(CorporateStructureRepository corporateStructureRepository, CorporateClientValidationService corporateClientValidationService, MessageSource messageSource, MessageService messageService) {
        this.corporateStructureRepository = corporateStructureRepository;
        this.corporateClientValidationService = corporateClientValidationService;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Override
    public CorporateStructureCrudResponseDto create(CorporateStructureCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);
            CorporateStructure corporateStructure = toEntity(request);
            corporateStructure = corporateStructureRepository.save(corporateStructure);
            return toResponseDto(corporateStructure);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<CorporateStructureCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}", entityName);
            Page<CorporateStructure> CorporateStructure = corporateStructureRepository.findAll(PageRequest.of(offset, limit));
            List<CorporateStructureCrudResponseDto> corporateStructureCrudResponseDtos = CorporateStructure.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(CorporateStructure.getTotalElements(), CorporateStructure.getTotalPages(), corporateStructureCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving all", e);
        }
    }

    @Override
    public CorporateStructureCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            CorporateStructure corporateStructure = corporateStructureRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateStructure.service.invalid.corporateStructure", new Object[]{id})));
            return toResponseDto(corporateStructure);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public CorporateStructureCrudResponseDto update(CorporateStructureCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            CorporateStructure corporateStructure = corporateStructureRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateStructure.service.invalid.corporateStructure", new Object[]{request.getId()})));
            CorporateStructure updateCorporateStructure = toEntity(request);
            updateCorporateStructure.setId(corporateStructure.getId());
            updateCorporateStructure = corporateStructureRepository.save(updateCorporateStructure);
            return toResponseDto(updateCorporateStructure);
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
            CorporateStructure corporateStructure = corporateStructureRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateStructure.service.invalid.corporateStructure", new Object[]{id})));
            corporateStructureRepository.delete(corporateStructure);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private CorporateStructure toEntity(CorporateStructureCrudRequestDto request) {
        log.debug("Mapping {} to entity", requestDto, entityName);
        CorporateClient corporateClient = corporateClientValidationService.validateCorporateClientExists(request.getId_corporate_client());
        return CorporateStructure.builder()
                .id_corporate_client(corporateClient)
                .name(request.getName())
                .position(request.getPosition())
                .build();
    }

    private CorporateStructureCrudResponseDto toResponseDto(CorporateStructure corporateStructure) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);
        return CorporateStructureCrudResponseDto.builder()
                .id(corporateStructure.getId())
                .id_corporate_client(corporateStructure.getId_corporate_client())
                .name(corporateStructure.getName())
                .position(corporateStructure.getPosition())
                .status(corporateStructure.getStatus())
                .createdAt(corporateStructure.getCreatedAt())
                .updatedAt(corporateStructure.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}