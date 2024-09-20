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
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public BoardOfDirectorCrudResponseDto create(BoardOfDirectorCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);
            BoardOfDirector boardOfDirector = toEntity(request);
            boardOfDirector = boardOfDirectorRepository.save(boardOfDirector);
            return toResponseDto(boardOfDirector);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<BoardOfDirectorCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<BoardOfDirector> boardOfDirector = boardOfDirectorRepository.findAll(PageRequest.of(offset, limit));
            List<BoardOfDirectorCrudResponseDto> boardOfDirectorCrudResponseDtos = boardOfDirector.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(boardOfDirector.getTotalElements(), boardOfDirector.getTotalPages(), boardOfDirectorCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public BoardOfDirectorCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            BoardOfDirector boardOfDirector = boardOfDirectorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("boardOfDirector.service.invalid.boardOfDirector", new Object[]{id})));
            return toResponseDto(boardOfDirector);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public BoardOfDirectorCrudResponseDto update(BoardOfDirectorCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            BoardOfDirector boardOfDirector = boardOfDirectorRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("boardOfDirector.service.invalid.boardOfDirector", new Object[]{request.getId()})));
            BoardOfDirector updateBoardOfDirector = toEntity(request);
            updateBoardOfDirector.setId(boardOfDirector.getId());
            updateBoardOfDirector = boardOfDirectorRepository.save(updateBoardOfDirector);
            return toResponseDto(updateBoardOfDirector);
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
            BoardOfDirector boardOfDirector = boardOfDirectorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("boardOfDirector.service.invalid.boardOfDirector", new Object[]{id})));
            boardOfDirectorRepository.delete(boardOfDirector);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private BoardOfDirector toEntity(BoardOfDirectorCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);
        CorporateClient corporateClient = corporateClientValidationService.validateCorporateClientExists(request.getId_corporate_client());

        return BoardOfDirector.builder()
                .id_corporate_client(corporateClient)
                .name(request.getName())
                .position(request.getPosition())
                .build();
    }

    private BoardOfDirectorCrudResponseDto toResponseDto(BoardOfDirector boardOfDirector) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);

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
        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}