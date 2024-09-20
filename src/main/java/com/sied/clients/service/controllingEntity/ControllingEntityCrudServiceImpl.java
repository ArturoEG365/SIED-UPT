package com.sied.clients.service.controllingEntity;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudRequestDto;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudUpdateRequestDto;
import com.sied.clients.dto.controllingEntity.response.ControllingEntityCrudResponseDto;
import com.sied.clients.entity.controllingEntity.ControllingEntity;
import com.sied.clients.entity.corporateClient.CorporateClient;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.controllingEntity.ControllingEntityRepository;
import com.sied.clients.repository.corporateClient.CorporateClientRepository;
import com.sied.clients.service.corporateClient.CorporateClientValidationService;
import com.sied.clients.util.security.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ControllingEntityCrudServiceImpl implements ControllingEntityCrudService {
    private final ControllingEntityRepository controllingEntityRepository;

    private final String entityName = ControllingEntity.class.getSimpleName();
    private final String responseDto = ControllingEntityCrudResponseDto.class.getSimpleName();
    private final String requestDto = ControllingEntityCrudRequestDto.class.getSimpleName();

    private final CorporateClientValidationService corporateClientValidationService;

    public final MessageSource messageSource;
    public final MessageService messageService;

    public ControllingEntityCrudServiceImpl(ControllingEntityRepository controllingEntityRepository, CorporateClientRepository corporateClientRepository, CorporateClientValidationService corporateClientValidationService, MessageSource messageSource, MessageService messageService) {
        this.controllingEntityRepository = controllingEntityRepository;
        this.corporateClientValidationService = corporateClientValidationService;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Override
    public ControllingEntityCrudResponseDto create(ControllingEntityCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);
            ControllingEntity controllingEntity = toEntity(request);
            controllingEntity = controllingEntityRepository.save(controllingEntity);
            return toResponseDto(controllingEntity);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<ControllingEntityCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<ControllingEntity> controllingEntity = controllingEntityRepository.findAll(PageRequest.of(offset, limit));
            List<ControllingEntityCrudResponseDto> controllingEntityCrudResponseDtos = controllingEntity.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(controllingEntity.getTotalElements(), controllingEntity.getTotalPages(), controllingEntityCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public ControllingEntityCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            ControllingEntity controllingEntity = controllingEntityRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("controllingEntity.service.invalid.controllingEntity", new Object[]{id})));
            return toResponseDto(controllingEntity);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public ControllingEntityCrudResponseDto update(ControllingEntityCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            ControllingEntity controllingEntity = controllingEntityRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("controllingEntity.service.invalid.controllingEntity", new Object[]{request.getId()})));
            ControllingEntity updateControllingEntity = toEntity(request);
            updateControllingEntity.setId(controllingEntity.getId());
            updateControllingEntity = controllingEntityRepository.save(updateControllingEntity);
            return toResponseDto(updateControllingEntity);
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
            ControllingEntity controllingEntity = controllingEntityRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("controllingEntity.service.invalid.controllingEntity", new Object[]{id})));
            controllingEntityRepository.delete(controllingEntity);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private ControllingEntity toEntity(ControllingEntityCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);
        CorporateClient corporateClient = corporateClientValidationService.validateCorporateClientExists(request.getId_corporate_client());

        return ControllingEntity.builder()
                .id_corporate_client(corporateClient)
                .name(request.getName())
                .build();
    }

    private ControllingEntityCrudResponseDto toResponseDto(ControllingEntity controllingEntity) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);

        return ControllingEntityCrudResponseDto.builder()
                .id(controllingEntity.getId())
                .id_corporate_client(controllingEntity.getId_corporate_client())
                .name(controllingEntity.getName())
                .status(controllingEntity.getStatus())
                .createdAt(controllingEntity.getCreatedAt())
                .updatedAt(controllingEntity.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}