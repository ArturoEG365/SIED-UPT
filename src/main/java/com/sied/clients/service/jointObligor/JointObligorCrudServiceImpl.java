package com.sied.clients.service.jointObligor;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.jointObligor.request.JointObligorCrudRequestDto;
import com.sied.clients.dto.jointObligor.request.JointObligorCrudUpdateRequestDto;
import com.sied.clients.dto.jointObligor.response.JointObligorCrudResponseDto;
import com.sied.clients.entity.address.Address;
import com.sied.clients.entity.client.Client;
import com.sied.clients.entity.jointObligor.JointObligor;
import com.sied.clients.entity.person.Person;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.address.AddressRepository;
import com.sied.clients.repository.client.ClientRepository;
import com.sied.clients.repository.jointObligor.JointObligorRepository;
import com.sied.clients.repository.person.PersonRepository;
import com.sied.clients.service.address.AddressValidationService;
import com.sied.clients.service.client.ClientValidationService;
import com.sied.clients.service.person.PersonValidationService;
import com.sied.clients.util.security.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JointObligorCrudServiceImpl implements JointObligorCrudService {
    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final JointObligorRepository jointObligorRepository;

    private final AddressValidationService addressValidationService;
    private final PersonValidationService personValidationService;
    private final ClientValidationService clientValidationService;

    private final String entityName = JointObligor.class.getSimpleName();
    private final String responseDto = JointObligorCrudResponseDto.class.getSimpleName();
    private final String requestDto = JointObligorCrudRequestDto.class.getSimpleName();

    public final MessageSource messageSource;
    public final MessageService messageService;

    public JointObligorCrudServiceImpl(ClientRepository clientRepository, PersonRepository personRepository, AddressRepository addressRepository, JointObligorRepository jointObligorRepository, AddressValidationService addressValidationService, PersonValidationService personValidationService, ClientValidationService clientValidationService, MessageSource messageSource, MessageService messageService) {
        this.clientRepository = clientRepository;
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.jointObligorRepository = jointObligorRepository;

        this.addressValidationService = addressValidationService;
        this.personValidationService = personValidationService;
        this.clientValidationService = clientValidationService;

        this.messageSource = messageSource;
        this.messageService = messageService;
    }

    @Override
    public JointObligorCrudResponseDto create(JointObligorCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);
            JointObligor jointObligor = toEntity(request);
            jointObligor = jointObligorRepository.save(jointObligor);
            return toResponseDto(jointObligor);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    @Override
    public PaginatedResponse<JointObligorCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}s", entityName);
            Page<JointObligor> jointObligor = jointObligorRepository.findAll(PageRequest.of(offset, limit));
            List<JointObligorCrudResponseDto> jointObligorCrudResponseDtos = jointObligor.map(this::toResponseDto).toList();
            return new PaginatedResponse<>(jointObligor.getTotalElements(), jointObligor.getTotalPages(), jointObligorCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public JointObligorCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            JointObligor jointObligor = jointObligorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("jointObligor.service.invalid.jointObligor", new Object[]{id})));
            return toResponseDto(jointObligor);
        } catch (EntityNotFoundException e) {
            log.error("Error retrieving {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    @Override
    public JointObligorCrudResponseDto update(JointObligorCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            JointObligor jointObligor = jointObligorRepository.findById(request.getId()).
                    orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("jointObligor.service.invalid.jointObligor", new Object[]{request.getId()})));
            JointObligor updateJointObligor = toEntity(request);
            updateJointObligor.setId(jointObligor.getId());
            updateJointObligor = jointObligorRepository.save(updateJointObligor);
            return toResponseDto(updateJointObligor);
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
            JointObligor jointObligor = jointObligorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("jointObligor.service.invalid.jointObligor", new Object[]{id})));
            jointObligorRepository.delete(jointObligor);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    private JointObligor toEntity(JointObligorCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);

        Client client = clientValidationService.validateClientExists(request.getId_client());
        Person person = personValidationService.validatePersonExists(request.getId_person());
        Address address = addressValidationService.validateAddressExists(request.getId_address());

        return JointObligor.builder()
                .client(client)
                .person(person)
                .address(address)
                .user(request.getId_user())
                .build();
    }

    private JointObligorCrudResponseDto toResponseDto(JointObligor jointObligor) {
        log.debug("Mapping {} entity to {}", entityName, responseDto);

        return JointObligorCrudResponseDto.builder()
                .id(jointObligor.getId())
                .id_client(jointObligor.getClient())
                .id_person(jointObligor.getPerson())
                .id_address(jointObligor.getAddress())
                .id_user(jointObligor.getUser())
                .status(jointObligor.getStatus())
                .build();
    }

    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());

        return new RuntimeException("Unexpected error while " + action + " " + entityName);
    }
}