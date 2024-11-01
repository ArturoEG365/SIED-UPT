package com.sied.clients.service.controllingEntity;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudRequestDto;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudUpdateRequestDto;
import com.sied.clients.dto.controllingEntity.response.ControllingEntityCrudResponseDto;
import com.sied.clients.entity.controllingEntity.ControllingEntity;
import com.sied.clients.entity.corporateClient.CorporateClient;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.controllingEntity.ControllingEntityRepository;
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

@Service
@Slf4j
public class ControllingEntityCrudServiceImpl implements ControllingEntityCrudService {
    private final ControllingEntityRepository repository;
    private final CorporateClientValidationService clientValidationService;
    private final MessageSource messageSource;
    private final MessageService messageService;

    private static final String ENTITY_NAME = ControllingEntity.class.getSimpleName();
    private static final String RESPONSE_DTO_NAME = ControllingEntityCrudResponseDto.class.getSimpleName();
    private static final String REQUEST_DTO_NAME = ControllingEntityCrudRequestDto.class.getSimpleName();

    public ControllingEntityCrudServiceImpl(
            ControllingEntityRepository repository,
            CorporateClientValidationService clientValidationService,
            MessageSource messageSource,
            MessageService messageService) {
        this.repository = repository;
        this.clientValidationService = clientValidationService;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Async
    @Override
    public CompletableFuture<ControllingEntityCrudResponseDto> createAsync(ControllingEntityCrudRequestDto request) {
        log.debug("Initiating asynchronous creation of {}", ENTITY_NAME);
        return toEntity(request)
                .thenCompose(entity -> CompletableFuture.supplyAsync(() -> repository.save(entity)))
                .thenApply(this::toResponseDto)
                .exceptionally(this::handleExceptionDuringCreation);
    }

    @Async
    @Override
    public CompletableFuture<PaginatedResponse<ControllingEntityCrudResponseDto>> fetchAll(int offset, int limit) {
        log.debug("Retrieving all entities for {} with pagination asynchronously", ENTITY_NAME);
        return CompletableFuture.supplyAsync(() -> {
            Page<ControllingEntity> page = repository.findAll(PageRequest.of(offset, limit));
            List<ControllingEntityCrudResponseDto> dtoList = page.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(page.getTotalElements(), page.getTotalPages(), dtoList);
        }).exceptionally(e -> {
            throw handleUnexpectedException("retrieving", (Exception) e);
        });
    }

    @Async
    @Override
    public CompletableFuture<ControllingEntityCrudResponseDto> get(Long id) {
        log.debug("Asynchronously retrieving {} by ID: {}", ENTITY_NAME, id);
        return CompletableFuture.supplyAsync(() -> repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                messageService.getMessage("controllingEntity.service.invalid.controllingEntity", new Object[]{id}))))
                .thenApply(this::toResponseDto)
                .exceptionally(this::handleExceptionDuringRetrieval);
    }

    @Async
    @Override
    public CompletableFuture<ControllingEntityCrudResponseDto> updateEntity(ControllingEntityCrudUpdateRequestDto request) {
        log.debug("Asynchronously updating {} with ID: {}", ENTITY_NAME, request.getId());
        return CompletableFuture.supplyAsync(() -> repository.findById(request.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                messageService.getMessage("controllingEntity.service.invalid.controllingEntity", new Object[]{request.getId()}))))
                .thenCompose(existingEntity -> toEntity(request)
                        .thenApply(updatedEntity -> {
                            updatedEntity.setId(existingEntity.getId());
                            return repository.save(updatedEntity);
                        })
                        .thenApply(this::toResponseDto))
                .exceptionally(this::handleExceptionDuringUpdate);
    }

    @Async
    @Override
    public CompletableFuture<Void> removeById(Long id) {
        log.debug("Asynchronously deleting {} with ID: {}", ENTITY_NAME, id);
        return CompletableFuture.runAsync(() -> {
            ControllingEntity entity = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                            messageService.getMessage("controllingEntity.service.invalid.controllingEntity", new Object[]{id})));
            repository.delete(entity);
        }).exceptionally(this::handleExceptionDuringDeletion);
    }

    private CompletableFuture<ControllingEntity> toEntity(ControllingEntityCrudRequestDto request) {
        log.debug("Mapping {} to {} entity asynchronously", REQUEST_DTO_NAME, ENTITY_NAME);
        return clientValidationService.validateCorporateClientExists(request.getCorporateClient())
                .thenApply(client -> ControllingEntity.builder()
                        .corporateClient(client)
                        .name(request.getName())
                        .build());
    }

    private CompletableFuture<ControllingEntity> toEntity(ControllingEntityCrudUpdateRequestDto request) {
        log.debug("Mapping {} to {} entity asynchronously", REQUEST_DTO_NAME, ENTITY_NAME);
        return clientValidationService.validateCorporateClientExists(request.getCorporateClient())
                .thenApply(client -> ControllingEntity.builder()
                        .corporateClient(client)
                        .name(request.getName())
                        .build());
    }

    private ControllingEntityCrudResponseDto toResponseDto(ControllingEntity entity) {
        log.debug("Mapping {} entity to {}", ENTITY_NAME, RESPONSE_DTO_NAME);
        return ControllingEntityCrudResponseDto.builder()
                .id(entity.getId())
                .corporateClient(entity.getCorporateClient())
                .name(entity.getName())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, ENTITY_NAME, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + ENTITY_NAME, e);
    }

    private ControllingEntityCrudResponseDto handleExceptionDuringCreation(Throwable ex) {
        return handleException("creating", ex);
    }

    private ControllingEntityCrudResponseDto handleExceptionDuringRetrieval(Throwable ex) {
        return handleException("retrieving", ex);
    }

    private ControllingEntityCrudResponseDto handleExceptionDuringUpdate(Throwable ex) {
        return handleException("updating", ex);
    }

    private Void handleExceptionDuringDeletion(Throwable ex) {
        handleException("deleting", ex);
        return null;
    }

    private <T> T handleException(String action, Throwable ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof EntityNotFoundException) {
            throw (EntityNotFoundException) cause;
        }
        throw handleUnexpectedException(action, (Exception) cause);
    }
}
