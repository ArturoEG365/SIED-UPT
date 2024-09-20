package com.sied.clients.service.client;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.client.request.ClientCrudUpdateRequestDto;
import com.sied.clients.dto.client.request.ClientCrudRequestDto;
import com.sied.clients.dto.client.response.ClientCrudResponseDto;
import com.sied.clients.entity.address.Address;
import com.sied.clients.entity.client.Client;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.address.AddressRepository;
import com.sied.clients.repository.client.ClientRepository;
import com.sied.clients.service.address.AddressValidationService;
import com.sied.clients.util.security.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClientCrudServiceImpl implements ClientCrudService{
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    private final AddressValidationService addressValidationService;

    private final String entityName = Client.class.getSimpleName();
    private final String responseDto = ClientCrudResponseDto.class.getSimpleName();
    private final String requestDto = ClientCrudRequestDto.class.getSimpleName();
    public final MessageSource messageSource;
    public final MessageService messageService;

    public ClientCrudServiceImpl(ClientRepository clientRepository, AddressRepository addressRepository, AddressValidationService addressValidationService, MessageSource messageSource, MessageService messageService) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.addressValidationService = addressValidationService;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Override
    public ClientCrudResponseDto create(ClientCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);
            Client client = toEntity(request);
            client = clientRepository.save(client);
            return toResponseDto(client);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<ClientCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<Client> client = clientRepository.findAll(PageRequest.of(offset, limit));
            List<ClientCrudResponseDto> clientCrudResponseDtos = client.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(client.getTotalElements(), client.getTotalPages(), clientCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public ClientCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            Client client = clientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("client.service.invalid.client", new Object[]{id})));
            return toResponseDto(client);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public ClientCrudResponseDto update(ClientCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            Client client = clientRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("client.service.invalid.client", new Object[]{request.getId()})));
            Client updateClient = toEntity(request);
            updateClient.setId(client.getId());
            updateClient = clientRepository.save(updateClient);
            return toResponseDto(updateClient);
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
            Client client = clientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("client.service.invalid.client", new Object[]{id})));
            clientRepository.delete(client);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private Client toEntity(ClientCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);

        Address address = addressValidationService.validateAddressExists(request.getId_address());

        return Client.builder()
                .id_address(address)
                .id_user(request.getId_user())
                .id_instance(request.getId_instance())
                .client_type(request.getClient_type())
                .build();
    }

    private ClientCrudResponseDto toResponseDto(Client client) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);

        return ClientCrudResponseDto.builder()
                .id(client.getId())
                .id_address(client.getId_address())
                .id_user(client.getId_user())
                .id_instance(client.getId_instance())
                .client_type(client.getClient_type())
                .status(client.getStatus())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}