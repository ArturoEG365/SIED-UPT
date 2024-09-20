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
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public ShareholderCrudResponseDto create(ShareholderCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);
            Shareholder shareholder = toEntity(request);
            shareholder = shareholderRepository.save(shareholder);
            return toResponseDto(shareholder);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<ShareholderCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<Shareholder> shareholder = shareholderRepository.findAll(PageRequest.of(offset, limit));
            List<ShareholderCrudResponseDto> shareholderCrudResponseDtos = shareholder.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(shareholder.getTotalElements(), shareholder.getTotalPages(), shareholderCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public ShareholderCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            Shareholder shareholder = shareholderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("shareholder.service.invalid.shareholder", new Object[]{id})));
            return toResponseDto(shareholder);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public ShareholderCrudResponseDto update(ShareholderCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            Shareholder shareholder = shareholderRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("shareholder.service.invalid.shareholder", new Object[]{request.getId()})));
            Shareholder updateShareholder = toEntity(request);
            updateShareholder.setId(shareholder.getId());
            updateShareholder = shareholderRepository.save(updateShareholder);
            return toResponseDto(updateShareholder);
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
            Shareholder shareholder = shareholderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("shareholder.service.invalid.shareholder", new Object[]{id})));
            shareholderRepository.delete(shareholder);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private Shareholder toEntity(ShareholderCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);
        CorporateClient corporateClient = corporateClientValidationService.validateCorporateClientExists(request.getId_corporate_client());

        return Shareholder.builder()
                .id_corporate_client(corporateClient)
                .name(request.getName())
                .ownershipPercentage(request.getOwnershipPercentage())
                .build();
    }

    private ShareholderCrudResponseDto toResponseDto(Shareholder shareholder) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);

        return ShareholderCrudResponseDto.builder()
                .id(shareholder.getId())
                .id_corporate_client(shareholder.getId_corporate_client())
                .name(shareholder.getName())
                .ownershipPercentage(shareholder.getOwnershipPercentage())
                .status(shareholder.getStatus())
                .createdAt(shareholder.getCreatedAt())
                .updatedAt(shareholder.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}