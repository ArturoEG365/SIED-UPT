package com.sied.clients.service.reference;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.reference.request.ReferenceCrudUpdateRequestDto;
import com.sied.clients.entity.client.Client;
import com.sied.clients.entity.reference.Reference;
import com.sied.clients.dto.reference.request.ReferenceCrudRequestDto;
import com.sied.clients.dto.reference.response.ReferenceCrudResponseDto;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.client.ClientRepository;
import com.sied.clients.repository.reference.ReferenceRepository;
import com.sied.clients.service.client.ClientValidationService;
import com.sied.clients.util.security.MessageService;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Implementación del servicio CRUD para las referencias.
 * Este servicio maneja la creación, obtención, actualización, eliminación y paginación de referencias en el sistema.
 */
@Slf4j
@Service
public class ReferenceCrudServiceImpl implements ReferenceCrudService {

    private final ClientRepository clientRepository;
    private final ReferenceRepository referenceRepository;
    private final MessageSource messageSource;
    private final MessageService messageService;
    private final ClientValidationService clientValidationService;

    private final String entityName = Reference.class.getSimpleName();
    private final String responseDto = ReferenceCrudResponseDto.class.getSimpleName();
    private final String requestDto = ReferenceCrudRequestDto.class.getSimpleName();

    /**
     * Constructor que inicializa los repositorios y servicios necesarios para el funcionamiento del servicio.
     *
     * @param clientRepository Repositorio para gestionar los clientes.
     * @param referenceRepository Repositorio para gestionar las referencias.
     * @param messageSource Fuente de mensajes para la internacionalización.
     * @param messageService Servicio para gestionar mensajes personalizados.
     * @param clientValidationService Servicio para la validación de clientes.
     */
    public ReferenceCrudServiceImpl(ClientRepository clientRepository, ReferenceRepository referenceRepository,
                                    MessageSource messageSource, MessageService messageService,
                                    ClientValidationService clientValidationService) {
        this.clientRepository = clientRepository;
        this.referenceRepository = referenceRepository;
        this.messageSource = messageSource;
        this.messageService = messageService;
        this.clientValidationService = clientValidationService;
    }

    /**
     * Crea una nueva referencia en el sistema.
     *
     * @param request DTO que contiene los datos para crear una referencia.
     * @return Un {@link ReferenceCrudResponseDto} que contiene los datos de la referencia recién creada.
     * @throws EntityNotFoundException Si el cliente relacionado no se encuentra.
     */
    @Override
    public ReferenceCrudResponseDto create(ReferenceCrudRequestDto request) {
        try {
            log.debug("Creating {}", entityName);

            Reference reference = toEntity(request);
            reference = referenceRepository.save(reference);

            return toResponseDto(reference);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("creating", e);
        }
    }

    /**
     * Obtiene todas las referencias con paginación.
     *
     * @param offset El número de página para la paginación.
     * @param limit El número máximo de resultados por página.
     * @return Un objeto {@link PaginatedResponse} que contiene una lista de referencias.
     */
    @Override
    public PaginatedResponse<ReferenceCrudResponseDto> getAll(int offset, int limit) {
        try {
            log.debug("Retrieving all {}", entityName);
            Page<Reference> references = referenceRepository.findAll(PageRequest.of(offset, limit));
            List<ReferenceCrudResponseDto> referenceCrudResponseDtos = references.map(this::toResponseDto).getContent();
            return new PaginatedResponse<>(references.getTotalElements(), references.getTotalPages(), referenceCrudResponseDtos);
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving all", e);
        }
    }

    /**
     * Obtiene una referencia por su ID.
     *
     * @param id El ID de la referencia que se desea obtener.
     * @return Un objeto {@link ReferenceCrudResponseDto} con los datos de la referencia.
     * @throws EntityNotFoundException Si no se encuentra una referencia con el ID proporcionado.
     */
    @Override
    public ReferenceCrudResponseDto get(Long id) {
        try {
            log.debug("Retrieving {} with ID: {}", entityName, id);
            Reference reference = referenceRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("reference.service.invalid.reference", new Object[]{id})));
            return toResponseDto(reference);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("retrieving", e);
        }
    }

    /**
     * Actualiza una referencia existente en el sistema.
     *
     * @param request DTO con los datos para actualizar la referencia.
     * @return Un {@link ReferenceCrudResponseDto} con los datos de la referencia actualizada.
     * @throws EntityNotFoundException Si la referencia a actualizar no se encuentra.
     */
    @Override
    public ReferenceCrudResponseDto update(ReferenceCrudUpdateRequestDto request) {
        try {
            log.debug("Updating {} with id {}", entityName, request.getId());
            Reference reference = referenceRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("reference.service.invalid.reference", new Object[]{request.getId()})));
            Reference updateReference = toEntity(request);
            updateReference.setId(reference.getId());
            updateReference = referenceRepository.save(updateReference);
            return toResponseDto(updateReference);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("updating", e);
        }
    }

    /**
     * Elimina una referencia por su ID.
     *
     * @param id El ID de la referencia que se desea eliminar.
     * @throws EntityNotFoundException Si no se encuentra la referencia a eliminar.
     */
    @Override
    public void delete(Long id) {
        try {
            log.debug("Deleting {} with ID: {}", entityName, id);
            Reference reference = referenceRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageService.getMessage("reference.service.invalid.reference", new Object[]{id})));
            referenceRepository.delete(reference);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting {}: {}", entityName, e.getMessage());
            throw e;
        } catch (Exception e) {
            throw handleUnexpectedException("deleting", e);
        }
    }

    /**
     * Convierte un DTO {@link ReferenceCrudRequestDto} en una entidad {@link Reference}.
     *
     * @param request El DTO con los datos de la referencia.
     * @return La entidad {@link Reference} que representa la referencia.
     */
    private Reference toEntity(ReferenceCrudRequestDto request) {
        log.debug("Mapping {} to {} entity", requestDto, entityName);
        Client client = clientValidationService.validateClientExists(request.getClient());

        return Reference.builder()
                .client(client)
                .referenceType(request.getReferenceType())
                .name(request.getName())
                .relationship(request.getRelationship())
                .phoneNumber(request.getPhoneNumber())
                .institutionName(request.getInstitutionName())
                .executive(request.getExecutive())
                .contractDate(request.getContractDate())
                .build();
    }

    /**
     * Convierte una entidad {@link Reference} en un DTO {@link ReferenceCrudResponseDto}.
     *
     * @param reference La entidad {@link Reference} que se desea convertir.
     * @return El DTO {@link ReferenceCrudResponseDto} correspondiente.
     */
    private ReferenceCrudResponseDto toResponseDto(Reference reference) {
        log.debug("Mapping {} entity to {} DTO", entityName, responseDto);
        return ReferenceCrudResponseDto.builder()
                .id(reference.getId())
                .client(reference.getClient())
                .referenceType(reference.getReferenceType())
                .name(reference.getName())
                .relationship(reference.getRelationship())
                .phoneNumber(reference.getPhoneNumber())
                .institutionName(reference.getInstitutionName())
                .executive(reference.getExecutive())
                .contractDate(reference.getContractDate())
                .status(reference.getStatus())
                .createdAt(reference.getCreatedAt())
                .updatedAt(reference.getUpdatedAt())
                .build();
    }

    /**
     * Maneja excepciones inesperadas y las envuelve en una excepción de tipo {@link RuntimeException}.
     *
     * @param action La acción que causó el error (crear, actualizar, eliminar).
     * @param e La excepción original.
     * @return Una nueva {@link RuntimeException} con un mensaje genérico de error.
     */
    private RuntimeException handleUnexpectedException(String action, Exception e) {
        log.error("Error {} {}: {}", action, entityName, e.getMessage());
        return new RuntimeException(messageService.getMessage("global.unexpected.error"));
    }
}
