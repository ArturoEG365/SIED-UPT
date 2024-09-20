package com.sied.clients.service.relatedPep;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.individualClient.request.IndividualClientCrudRequestDto;
import com.sied.clients.dto.individualClient.response.IndividualClientCrudResponseDto;
import com.sied.clients.dto.relatedPep.request.RelatedPepCrudRequestDto;
import com.sied.clients.dto.relatedPep.request.RelatedPepCrudUpdateRequestDto;
import com.sied.clients.dto.relatedPep.response.RelatedPepCrudResponseDto;
import com.sied.clients.entity.individualClient.IndividualClient;
import com.sied.clients.entity.relatedPep.RelatedPep;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.individualClient.IndividualClientRepository;
import com.sied.clients.repository.relatedPep.RelatedPepRepository;
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
public class RelatedPepCrudServiceImpl implements RelatedPepCrudService {
    private final IndividualClientRepository individualClientRepository;
    private final RelatedPepRepository relatedPepRepository;

    private final IndividualClientValidationService individualClientValidationService;

    private final String entityName = IndividualClient.class.getSimpleName();
    private final String responseDto = IndividualClientCrudResponseDto.class.getSimpleName();
    private final String requestDto = IndividualClientCrudRequestDto.class.getSimpleName();

    public final MessageSource messageSource;
    public final MessageService messageService;


    public RelatedPepCrudServiceImpl(IndividualClientRepository individualClientRepository, RelatedPepRepository relatedPepRepository, IndividualClientValidationService individualClientValidationService, MessageSource messageSource, MessageService messageService) {
        this.individualClientRepository = individualClientRepository;
        this.relatedPepRepository = relatedPepRepository;

        this.individualClientValidationService = individualClientValidationService;

        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Override
    public RelatedPepCrudResponseDto create(RelatedPepCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);
            RelatedPep relatedPep = toEntity(request);
            relatedPep = relatedPepRepository.save(relatedPep);
            return toResponseDto(relatedPep);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<RelatedPepCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<RelatedPep> relatedPep = relatedPepRepository.findAll(PageRequest.of(offset, limit));
            List<RelatedPepCrudResponseDto> relatedPepCrudResponseDtos = relatedPep.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(relatedPep.getTotalElements(), relatedPep.getTotalPages(), relatedPepCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public RelatedPepCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            RelatedPep relatedPep = relatedPepRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("relatedPep.service.invalid.relatedPep", new Object[]{id})));
            return toResponseDto(relatedPep);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public RelatedPepCrudResponseDto update(RelatedPepCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            RelatedPep relatedPep = relatedPepRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("relatedPep.service.invalid.relatedPep", new Object[]{request.getId()})));
            RelatedPep updateRelatedPep = toEntity(request);
            updateRelatedPep.setId(relatedPep.getId());
            updateRelatedPep = relatedPepRepository.save(updateRelatedPep);
            return toResponseDto(updateRelatedPep);
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
            RelatedPep relatedPep = relatedPepRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("relatedPep.service.invalid.relatedPep", new Object[]{id})));
            relatedPepRepository.delete(relatedPep);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private RelatedPep toEntity(RelatedPepCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);

        IndividualClient individualClient = individualClientValidationService.validateIndividualClientExists(request.getIndividualClient());

        return RelatedPep.builder()
                .individualClient(individualClient)
                .relationship(request.getRelationship())
                .position(request.getPosition())
                .build();
    }

    private RelatedPepCrudResponseDto toResponseDto(RelatedPep relatedPep) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);

        return RelatedPepCrudResponseDto.builder()
                .id(relatedPep.getId())
                .individualClient(relatedPep.getIndividualClient())
                .relationship(relatedPep.getRelationship())
                .position(relatedPep.getPosition())
                .status(relatedPep.getStatus())
                .createdAt(relatedPep.getCreatedAt())
                .updatedAt(relatedPep.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());

        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}