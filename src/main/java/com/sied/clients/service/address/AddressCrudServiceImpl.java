package com.sied.clients.service.address;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.address.request.AddressCrudUpdateRequestDto;
import com.sied.clients.dto.address.request.AddressCrudRequestDto;
import com.sied.clients.dto.address.response.AddressCrudResponseDto;
import com.sied.clients.entity.address.Address;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.address.AddressRepository;
import com.sied.clients.util.security.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AddressCrudServiceImpl implements AddressCrudService{
    private final AddressRepository addressRepository;
    private final String entityName = Address.class.getSimpleName();
    private final String responseDto = AddressCrudResponseDto.class.getSimpleName();
    private final String requestDto = AddressCrudRequestDto.class.getSimpleName();
    public final MessageSource messageSource;
    public final MessageService messageService;

    public AddressCrudServiceImpl(AddressRepository addressRepository, MessageSource messageSource, MessageService messageService) {
        this.addressRepository = addressRepository;
        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Override
    public AddressCrudResponseDto create(AddressCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);
            Address address = toEntity(request);
            address = addressRepository.save(address);
            return toResponseDto(address);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<AddressCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<Address> address = addressRepository.findAll(PageRequest.of(offset, limit));
            List<AddressCrudResponseDto> addressCrudResponseDtos = address.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(address.getTotalElements(), address.getTotalPages(), addressCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public AddressCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            Address address = addressRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("address.service.invalid.address", new Object[]{id})));
            return toResponseDto(address);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public AddressCrudResponseDto update(AddressCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            Address address = addressRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("address.service.invalid.address", new Object[]{request.getId()})));
            Address updateAddress = toEntity(request);
            updateAddress.setId(address.getId());
            updateAddress = addressRepository.save(updateAddress);
            return toResponseDto(updateAddress);
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
            Address address = addressRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("address.service.invalid.address", new Object[]{id})));
            addressRepository.delete(address);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private Address toEntity(AddressCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);

        return Address.builder()
                .residenceCountry(request.getResidenceCountry())
                .residenceState(request.getResidenceState())
                .street(request.getStreet())
                .betweenStreet(request.getBetweenStreet())
                .externalNumber(request.getExternalNumber())
                .internalNumber(request.getInternalNumber())
                .postalCode(request.getPostalCode())
                .neighborhood(request.getNeighborhood())
                .municipality(request.getMunicipality())
                .city(request.getCity())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .requestLatitude(request.getRequestLatitude())
                .requestLongitude(request.getRequestLongitude())
                .build();
    }

    private AddressCrudResponseDto toResponseDto(Address address) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);

        return AddressCrudResponseDto.builder()
                .id(address.getId())
                .residenceCountry(address.getResidenceCountry())
                .residenceState(address.getResidenceState())
                .street(address.getStreet())
                .betweenStreet(address.getBetweenStreet())
                .externalNumber(address.getExternalNumber())
                .internalNumber(address.getInternalNumber())
                .postalCode(address.getPostalCode())
                .neighborhood(address.getNeighborhood())
                .municipality(address.getMunicipality())
                .city(address.getCity())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .requestLatitude(address.getRequestLatitude())
                .requestLongitude(address.getRequestLongitude())
                .status(address.getStatus())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}