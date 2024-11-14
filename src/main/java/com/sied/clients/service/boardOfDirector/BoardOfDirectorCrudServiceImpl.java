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

/**
 * Implementación de la interfaz BoardOfDirectorCrudService para operaciones CRUD de la entidad BoardOfDirector.
 * Proporciona métodos para crear, obtener, actualizar y eliminar registros de miembros de la junta directiva
 * de manera asíncrona.
 */
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

    /**
     * Constructor para la clase BoardOfDirectorCrudServiceImpl.
     *
     * @param boardOfDirectorRepository Repositorio para realizar operaciones de persistencia en BoardOfDirector.
     * @param corporateClientValidationService Servicio para validar la existencia de un CorporateClient.
     * @param messageSource Fuente de mensajes para internacionalización.
     * @param messageService Servicio para mensajes personalizados en las respuestas.
     */
    public BoardOfDirectorCrudServiceImpl(BoardOfDirectorRepository boardOfDirectorRepository, CorporateClientValidationService corporateClientValidationService, MessageSource messageSource, MessageService messageService) {
        this.boardOfDirectorRepository = boardOfDirectorRepository;
        this.corporateClientValidationService = corporateClientValidationService;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    /**
     * Crea un nuevo registro de BoardOfDirector de manera asíncrona.
     *
     * @param request DTO con la información del BoardOfDirector a crear.
     * @return Un CompletableFuture con el DTO de respuesta del BoardOfDirector creado.
     */
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

    /**
     * Obtiene una lista paginada de todos los BoardOfDirector de manera asíncrona.
     *
     * @param offset Índice de inicio para la paginación.
     * @param limit Cantidad máxima de registros a devolver.
     * @return Un CompletableFuture con la respuesta paginada de BoardOfDirectorCrudResponseDto.
     */
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

    /**
     * Obtiene un BoardOfDirector por su ID de manera asíncrona.
     *
     * @param id ID del BoardOfDirector a obtener.
     * @return Un CompletableFuture con el DTO de respuesta del BoardOfDirector obtenido.
     */
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

    /**
     * Actualiza un BoardOfDirector de manera asíncrona.
     *
     * @param request DTO con la información actualizada del BoardOfDirector.
     * @return Un CompletableFuture con el DTO de respuesta del BoardOfDirector actualizado.
     */
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

    /**
     * Elimina un BoardOfDirector por su ID de manera asíncrona.
     *
     * @param id ID del BoardOfDirector a eliminar.
     * @return Un CompletableFuture que indica la finalización de la operación de eliminación.
     */
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

    /**
     * Convierte un BoardOfDirectorCrudRequestDto en una entidad BoardOfDirector.
     *
     * @param request DTO de solicitud de creación.
     * @return Un CompletableFuture con la entidad BoardOfDirector creada a partir del DTO.
     */
    private CompletableFuture<BoardOfDirector> toEntity(BoardOfDirectorCrudRequestDto request) {
        log.debug("Mapping {} to {} entity asynchronously", requestDto, entityName);
        return corporateClientValidationService.validateCorporateClientExists(request.getCorporateClient())
                .thenApply(corporateClient -> BoardOfDirector.builder()
                        .corporateClient(corporateClient)
                        .name(request.getName())
                        .position(request.getPosition())
                        .build());
    }

    /**
     * Convierte un BoardOfDirectorCrudUpdateRequestDto en una entidad BoardOfDirector.
     *
     * @param request DTO de solicitud de actualización.
     * @return Un CompletableFuture con la entidad BoardOfDirector actualizada a partir del DTO.
     */
    private CompletableFuture<BoardOfDirector> toEntity(BoardOfDirectorCrudUpdateRequestDto request) {
        log.debug("Mapping {} to {} entity asynchronously", requestDto, entityName);
        return corporateClientValidationService.validateCorporateClientExists(request.getCorporateClient())
                .thenApply(corporateClient -> BoardOfDirector.builder()
                        .corporateClient(corporateClient)
                        .name(request.getName())
                        .position(request.getPosition())
                        .build());
    }

    /**
     * Convierte una entidad BoardOfDirector en un DTO de respuesta BoardOfDirectorCrudResponseDto.
     *
     * @param boardOfDirector Entidad BoardOfDirector a convertir.
     * @return El DTO de respuesta correspondiente.
     */
    private BoardOfDirectorCrudResponseDto toResponseDto(BoardOfDirector boardOfDirector) {
        log.debug("Mapping {} entity to {} asynchronously", entityName, responseDto);
        return BoardOfDirectorCrudResponseDto.builder()
                .id(boardOfDirector.getId())
                .corporateClient(boardOfDirector.getCorporateClient())
                .name(boardOfDirector.getName())
                .position(boardOfDirector.getPosition())
                .status(boardOfDirector.getStatus())
                .createdAt(boardOfDirector.getCreatedAt())
                .updatedAt(boardOfDirector.getUpdatedAt())
                .build();
    }

    /**
     * Maneja excepciones inesperadas lanzadas durante las operaciones CRUD.
     *
     * @param action La acción que se estaba realizando cuando ocurrió la excepción.
     * @param e La excepción lanzada.
     * @return Una RuntimeException con el mensaje de error y la causa original.
     */
    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName, e);
    }
}
