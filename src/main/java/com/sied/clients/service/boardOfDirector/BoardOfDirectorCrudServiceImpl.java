package com.sied.clients.service.boardOfDirector;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.boardOfDirector.request.BoardOfDirectorCrudRequestDto;
import com.sied.clients.dto.boardOfDirector.request.BoardOfDirectorCrudUpdateRequestDto;
import com.sied.clients.dto.boardOfDirector.response.BoardOfDirectorCrudResponseDto;
import com.sied.clients.entity.boardOfDirector.BoardOfDirector;
import com.sied.clients.entity.corporateClient.CorporateClient;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.boardOfDirector.BoardOfDirectorRepository;
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
public class BoardOfDirectorCrudServiceImpl implements BoardOfDirectorCrudService {
    private final BoardOfDirectorRepository boardOfDirectorRepository;

    private final String entityName = BoardOfDirector.class.getSimpleName();
    private final String responseDto = BoardOfDirectorCrudResponseDto.class.getSimpleName();
    private final String requestDto = BoardOfDirectorCrudRequestDto.class.getSimpleName();

    public final MessageSource messageSource;
    public final MessageService messageService;

    private final CorporateClientValidationService corporateClientValidationService;

    public BoardOfDirectorCrudServiceImpl(BoardOfDirectorRepository boardOfDirectorRepository, CorporateClientValidationService corporateClientValidationService, MessageSource messageSource, MessageService messageService) {
        this.boardOfDirectorRepository = boardOfDirectorRepository;
        this.corporateClientValidationService = corporateClientValidationService;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Async
    @Override
    public CompletableFuture<BoardOfDirectorCrudResponseDto> create(BoardOfDirectorCrudRequestDto request) {
        log.debug("Creating {} asynchronously", entityName);
        return toEntity(request)
                .thenCompose(boardOfDirector -> CompletableFuture.supplyAsync(() -> {
                    BoardOfDirector savedBoardOfDirector = boardOfDirectorRepository.save(boardOfDirector);
                    return toResponseDto(savedBoardOfDirector);
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
    public CompletableFuture<PaginatedResponse<BoardOfDirectorCrudResponseDto>> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s asynchronously", entityName);
            Page<BoardOfDirector> boardOfDirectorPage = boardOfDirectorRepository.findAll(PageRequest.of(offset, limit));
            List<BoardOfDirectorCrudResponseDto> boardOfDirectorCrudResponseDtos = boardOfDirectorPage.map(this::toResponseDto).toList();
            PaginatedResponse<BoardOfDirectorCrudResponseDto> response = new PaginatedResponse<>(boardOfDirectorPage.getTotalElements(), boardOfDirectorPage.getTotalPages(), boardOfDirectorCrudResponseDtos);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Async
    @Override
    public CompletableFuture<BoardOfDirectorCrudResponseDto> get(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            log.debug("Retrieving {} with ID: {} asynchronously", entityName, id);
            BoardOfDirector boardOfDirector = boardOfDirectorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("boardOfDirector.service.invalid.boardOfDirector", new Object[]{id})));
            return toResponseDto(boardOfDirector);
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
    public CompletableFuture<BoardOfDirectorCrudResponseDto> update(BoardOfDirectorCrudUpdateRequestDto request) {
        log.debug("Updating {} with id {} asynchronously", entityName, request.getId());
        return CompletableFuture.supplyAsync(() -> {
            BoardOfDirector boardOfDirector = boardOfDirectorRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("boardOfDirector.service.invalid.boardOfDirector", new Object[]{request.getId()})));
            return boardOfDirector;
        }).thenCompose(existingBoardOfDirector -> toEntity(request)
                .thenCompose(updatedBoardOfDirector -> CompletableFuture.supplyAsync(() -> {
                    updatedBoardOfDirector.setId(existingBoardOfDirector.getId());
                    BoardOfDirector savedBoardOfDirector = boardOfDirectorRepository.save(updatedBoardOfDirector);
                    return toResponseDto(savedBoardOfDirector);
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
            BoardOfDirector boardOfDirector = boardOfDirectorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("boardOfDirector.service.invalid.boardOfDirector", new Object[]{id})));
            boardOfDirectorRepository.delete(boardOfDirector);
        }).exceptionally(ex -> {
            Throwable cause = ex.getCause();
            if (cause instanceof EntityNotFoundException) {
                throw (EntityNotFoundException) cause;
            } else {
                throw handleUnexpectedException("deleting", (Exception) cause);
            }
        });
    }

    private CompletableFuture<BoardOfDirector> toEntity(BoardOfDirectorCrudRequestDto request) {
        log.debug("Mapping {} to {} entity asynchronously", requestDto, entityName);
        return corporateClientValidationService.validateCorporateClientExists(request.getId_corporate_client())
                .thenApply(corporateClient -> BoardOfDirector.builder()
                        .id_corporate_client(corporateClient)
                        .name(request.getName())
                        .position(request.getPosition())
                        .build());
    }

    private CompletableFuture<BoardOfDirector> toEntity(BoardOfDirectorCrudUpdateRequestDto request) {
        log.debug("Mapping {} to {} entity asynchronously", requestDto, entityName);
        return corporateClientValidationService.validateCorporateClientExists(request.getId_corporate_client())
                .thenApply(corporateClient -> BoardOfDirector.builder()
                        .id_corporate_client(corporateClient)
                        .name(request.getName())
                        .position(request.getPosition())
                        .build());
    }

    private BoardOfDirectorCrudResponseDto toResponseDto(BoardOfDirector boardOfDirector) {
        log.debug("Mapping {} entity to {} asynchronously", entityName, responseDto);

        return BoardOfDirectorCrudResponseDto.builder()
                .id(boardOfDirector.getId())
                .id_corporate_client(boardOfDirector.getId_corporate_client())
                .name(boardOfDirector.getName())
                .position(boardOfDirector.getPosition())
                .status(boardOfDirector.getStatus())
                .createdAt(boardOfDirector.getCreatedAt())
                .updatedAt(boardOfDirector.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName, e);
    }
}
