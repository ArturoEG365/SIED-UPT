package com.sied.clients.service.corporateStructure;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.corporateStructure.request.CorporateStructureCrudRequestDto;
import com.sied.clients.dto.corporateStructure.request.CorporateStructureCrudUpdateRequestDto;
import com.sied.clients.dto.corporateStructure.response.CorporateStructureCrudResponseDto;
import com.sied.clients.entity.corporateStructure.CorporateStructure;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.corporateStructure.CorporateStructureRepository;
import com.sied.clients.service.corporateClient.CorporateClientValidationService;
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

    @Async
    @Override
    public CompletableFuture<CorporateStructureCrudResponseDto> create(CorporateStructureCrudRequestDto request) {
        log.debug("Creating {} asynchronously", entityName);
        return toEntity(request)
                .thenCompose(corporateStructure -> CompletableFuture.supplyAsync(() -> {
                    CorporateStructure savedStructure = corporateStructureRepository.save(corporateStructure);
                    return toResponseDto(savedStructure);
                }))
                .exceptionally(ex -> {
                    Throwable cause = ex.getCause();
                    if (cause instanceof EntityNotFoundException) {
                        throw (EntityNotFoundException) cause;
                    } else {
                        throw handleUnexpectedException("creating", (Exception) cause);
                    }
                });
    }

    @Async
    @Override
    public CompletableFuture<PaginatedResponse<CorporateStructureCrudResponseDto>> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {} asynchronously", entityName);
            Page<CorporateStructure> corporateStructurePage = corporateStructureRepository.findAll(PageRequest.of(offset, limit));
            List<CorporateStructureCrudResponseDto> responseDtos = corporateStructurePage.map(this::toResponseDto).toList();
            PaginatedResponse<CorporateStructureCrudResponseDto> response = new PaginatedResponse<>(corporateStructurePage.getTotalElements(), corporateStructurePage.getTotalPages(), responseDtos);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving all", e);
        }
    }

    @Async
    @Override
    public CompletableFuture<CorporateStructureCrudResponseDto> get(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            log.debug("Retrieving {} with ID: {} asynchronously", entityName, id);
            CorporateStructure corporateStructure = corporateStructureRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateStructure.service.invalid.corporateStructure", new Object[]{id})));
            return toResponseDto(corporateStructure);
        }).exceptionally(ex -> {
            Throwable cause = ex.getCause();
            if (cause instanceof EntityNotFoundException) {
                throw (EntityNotFoundException) cause;
            } else {
                throw handleUnexpectedException("retrieving", (Exception) cause);
            }
        });
    }

    @Async
    @Override
    public CompletableFuture<CorporateStructureCrudResponseDto> update(CorporateStructureCrudUpdateRequestDto request) {
        log.debug("Updating {} with id {} asynchronously", entityName, request.getId());
        return CompletableFuture.supplyAsync(() -> {
            CorporateStructure existingStructure = corporateStructureRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateStructure.service.invalid.corporateStructure", new Object[]{request.getId()})));
            return existingStructure;
        }).thenCompose(existingStructure -> toEntity(request)
                .thenCompose(updatedStructure -> CompletableFuture.supplyAsync(() -> {
                    updatedStructure.setId(existingStructure.getId());
                    CorporateStructure savedStructure = corporateStructureRepository.save(updatedStructure);
                    return toResponseDto(savedStructure);
                }))
        ).exceptionally(ex -> {
            Throwable cause = ex.getCause();
            if (cause instanceof EntityNotFoundException) {
                throw (EntityNotFoundException) cause;
            } else {
                throw handleUnexpectedException("updating", (Exception) cause);
            }
        });
    }

    @Async
    @Override
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
            log.debug("Deleting {} with ID: {} asynchronously", entityName, id);
            CorporateStructure corporateStructure = corporateStructureRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("corporateStructure.service.invalid.corporateStructure", new Object[]{id})));
            corporateStructureRepository.delete(corporateStructure);
        }).exceptionally(ex -> {
            Throwable cause = ex.getCause();
            if (cause instanceof EntityNotFoundException) {
                throw (EntityNotFoundException) cause;
            } else {
                throw handleUnexpectedException("deleting", (Exception) cause);
            }
        });
    }

    private CompletableFuture<CorporateStructure> toEntity(CorporateStructureCrudRequestDto request) {
        log.debug("Mapping {} to entity asynchronously", requestDto);
        return corporateClientValidationService.validateCorporateClientExists(request.getId_corporate_client())
                .thenApply(corporateClient -> CorporateStructure.builder()
                        .id_corporate_client(corporateClient)
                        .name(request.getName())
                        .position(request.getPosition())
                        .build());
    }

    private CompletableFuture<CorporateStructure> toEntity(CorporateStructureCrudUpdateRequestDto request) {
        log.debug("Mapping {} to entity asynchronously", requestDto);
        return corporateClientValidationService.validateCorporateClientExists(request.getId_corporate_client())
                .thenApply(corporateClient -> CorporateStructure.builder()
                        .id_corporate_client(corporateClient)
                        .name(request.getName())
                        .position(request.getPosition())
                        .build());
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
        return new RuntimeException("Unexpected error while " + action + " " + entityName, e);
    }
}
