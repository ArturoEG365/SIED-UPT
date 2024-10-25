package com.sied.clients.service.shareholder;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.shareholder.request.ShareholderCrudRequestDto;
import com.sied.clients.dto.shareholder.request.ShareholderCrudUpdateRequestDto;
import com.sied.clients.dto.shareholder.response.ShareholderCrudResponseDto;
import com.sied.clients.entity.corporateClient.CorporateClient;
import com.sied.clients.entity.shareholder.Shareholder;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.shareholder.ShareholderRepository;
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
public class ShareholderCrudServiceImpl implements ShareholderCrudService {
    private final ShareholderRepository shareholderRepository;

    private final String entityName = Shareholder.class.getSimpleName();
    private final String responseDto = ShareholderCrudResponseDto.class.getSimpleName();
    private final String requestDto = ShareholderCrudRequestDto.class.getSimpleName();

    private final CorporateClientValidationService corporateClientValidationService;

    public final MessageSource messageSource;
    public final MessageService messageService;

    public ShareholderCrudServiceImpl(ShareholderRepository shareholderRepository, CorporateClientValidationService corporateClientValidationService, MessageSource messageSource, MessageService messageService) {
        this.shareholderRepository = shareholderRepository;
        this.corporateClientValidationService = corporateClientValidationService;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Async
    @Override
    public CompletableFuture<ShareholderCrudResponseDto> create(ShareholderCrudRequestDto request) {
        log.debug("Creating {} asynchronously", entityName);
        return toEntity(request)
                .thenCompose(shareholder -> CompletableFuture.supplyAsync(() -> {
                    Shareholder savedShareholder = shareholderRepository.save(shareholder);
                    return toResponseDto(savedShareholder);
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
    public CompletableFuture<PaginatedResponse<ShareholderCrudResponseDto>> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s asynchronously", entityName);
            Page<Shareholder> shareholderPage = shareholderRepository.findAll(PageRequest.of(offset, limit));
            List<ShareholderCrudResponseDto> shareholderCrudResponseDtos = shareholderPage.map(this::toResponseDto).toList();
            PaginatedResponse<ShareholderCrudResponseDto> response = new PaginatedResponse<>(shareholderPage.getTotalElements(), shareholderPage.getTotalPages(), shareholderCrudResponseDtos);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Async
    @Override
    public CompletableFuture<ShareholderCrudResponseDto> get(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            log.debug("Retrieving {} with ID: {} asynchronously", entityName, id);
            Shareholder shareholder = shareholderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("shareholder.service.invalid.shareholder", new Object[]{id})));
            return toResponseDto(shareholder);
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
    public CompletableFuture<ShareholderCrudResponseDto> update(ShareholderCrudUpdateRequestDto request) {
        log.debug("Updating {} with id {} asynchronously", entityName, request.getId());
        return CompletableFuture.supplyAsync(() -> {
            Shareholder existingShareholder = shareholderRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("shareholder.service.invalid.shareholder", new Object[]{request.getId()})));
            return existingShareholder;
        }).thenCompose(existingShareholder -> toEntity(request)
                .thenCompose(updatedShareholder -> CompletableFuture.supplyAsync(() -> {
                    updatedShareholder.setId(existingShareholder.getId());
                    Shareholder savedShareholder = shareholderRepository.save(updatedShareholder);
                    return toResponseDto(savedShareholder);
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
            Shareholder shareholder = shareholderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("shareholder.service.invalid.shareholder", new Object[]{id})));
            shareholderRepository.delete(shareholder);
        }).exceptionally(ex -> {
            Throwable cause = ex.getCause();
            if (cause instanceof EntityNotFoundException) {
                throw (EntityNotFoundException) cause;
            } else {
                throw handleUnexpectedException("deleting", (Exception) cause);
            }
        });
    }

    private CompletableFuture<Shareholder> toEntity(ShareholderCrudRequestDto request) {
        log.debug("Mapping {} to {} entity asynchronously", requestDto, entityName);
        return corporateClientValidationService.validateCorporateClientExists(request.getCorporateClient())
                .thenApply(corporateClient -> Shareholder.builder()
                        .corporateClient(corporateClient)
                        .name(request.getName())
                        .ownershipPercentage(request.getOwnershipPercentage())
                        .build());
    }

    private CompletableFuture<Shareholder> toEntity(ShareholderCrudUpdateRequestDto request) {
        log.debug("Mapping {} to {} entity asynchronously", requestDto, entityName);
        return corporateClientValidationService.validateCorporateClientExists(request.getCorporateClient())
                .thenApply(corporateClient -> Shareholder.builder()
                        .corporateClient(corporateClient)
                        .name(request.getName())
                        .ownershipPercentage(request.getOwnershipPercentage())
                        .build());
    }

    private ShareholderCrudResponseDto toResponseDto(Shareholder shareholder) {
        log.debug("Mapping {} entity to {} asynchronously", entityName, responseDto);

        return ShareholderCrudResponseDto.builder()
                .id(shareholder.getId())
                .corporateClient(shareholder.getCorporateClient())
                .name(shareholder.getName())
                .ownershipPercentage(shareholder.getOwnershipPercentage())
                .status(shareholder.getStatus())
                .createdAt(shareholder.getCreatedAt())
                .updatedAt(shareholder.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName, e);
    }
}
