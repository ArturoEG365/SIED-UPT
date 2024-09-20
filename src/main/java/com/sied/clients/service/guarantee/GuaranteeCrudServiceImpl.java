package com.sied.clients.service.guarantee;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.guarantee.request.GuaranteeCrudRequestDto;
import com.sied.clients.dto.guarantee.request.GuaranteeCrudUpdateRequestDto;
import com.sied.clients.dto.guarantee.response.GuaranteeCrudResponseDto;
import com.sied.clients.entity.client.Client;
import com.sied.clients.entity.guarantee.Guarantee;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.client.ClientRepository;
import com.sied.clients.repository.guarantee.GuaranteeRepository;
import com.sied.clients.service.client.ClientValidationService;
import com.sied.clients.util.security.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

import java.util.List;

@Slf4j
@Service
public class GuaranteeCrudServiceImpl implements GuaranteeCrudService {
    private final ClientRepository clientRepository;
    private final GuaranteeRepository guaranteeRepository;

    private final String entityName = Guarantee.class.getSimpleName();
    private final String responseDto = GuaranteeCrudResponseDto.class.getSimpleName();
    private final String requestDto = GuaranteeCrudRequestDto.class.getSimpleName();

    public final MessageSource messageSource;
    public final MessageService messageService;

    private final ClientValidationService clientValidationService;

    public GuaranteeCrudServiceImpl(ClientRepository clientRepository, GuaranteeRepository guaranteeRepository, MessageSource messageSource, MessageService messageService, ClientValidationService clientValidationService) {
        this.clientRepository = clientRepository;
        this.guaranteeRepository = guaranteeRepository;
        this.messageSource = messageSource;
        this.messageService = messageService;
        this.clientValidationService = clientValidationService;
    }

    @Async
    @Override
    public CompletableFuture<GuaranteeCrudResponseDto> create(GuaranteeCrudRequestDto request) {
        try {
            log.debug("Creating {} asynchronously", entityName);
            Guarantee guarantee = toEntity(request);
            guarantee = guaranteeRepository.save(guarantee);
            return CompletableFuture.completedFuture(toResponseDto(guarantee));
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<GuaranteeCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<Guarantee> guarantee = guaranteeRepository.findAll(PageRequest.of(offset, limit));
            List<GuaranteeCrudResponseDto> guaranteeCrudResponseDtos = guarantee.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(guarantee.getTotalElements(), guarantee.getTotalPages(), guaranteeCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public GuaranteeCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            Guarantee guarantee = guaranteeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("guarantee.service.invalid.guarantee", new Object[]{id})));
            return toResponseDto(guarantee);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public GuaranteeCrudResponseDto update(GuaranteeCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            Guarantee guarantee = guaranteeRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("guarantee.service.invalid.guarantee", new Object[]{request.getId()})));
            Guarantee updateGuarantee = toEntity(request);
            updateGuarantee.setId(guarantee.getId());
            updateGuarantee = guaranteeRepository.save(updateGuarantee);
            return toResponseDto(updateGuarantee);
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
            Guarantee guarantee = guaranteeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("guarantee.service.invalid.guarantee", new Object[]{id})));
            guaranteeRepository.delete(guarantee);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private Guarantee toEntity(GuaranteeCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);
        Client client = clientValidationService.validateClientExists(request.getId_client());

        return Guarantee.builder()
                .id_client(client)
                .name(request.getName())
                .build();
    }

    private GuaranteeCrudResponseDto toResponseDto(Guarantee guarantee) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);

        return GuaranteeCrudResponseDto.builder()
                .id(guarantee.getId())
                .id_client(guarantee.getId_client())
                .name(guarantee.getName())
                .status(guarantee.getStatus())
                .createdAt(guarantee.getCreatedAt())
                .updatedAt(guarantee.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}