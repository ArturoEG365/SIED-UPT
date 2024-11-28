package com.sied.clients.controller.reference;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.reference.request.ReferenceCrudRequestDto;
import com.sied.clients.dto.reference.request.ReferenceCrudUpdateRequestDto;
import com.sied.clients.dto.reference.response.ReferenceCrudResponseDto;
import com.sied.clients.service.reference.ReferenceCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar las referencias en el sistema.
 * Proporciona los métodos necesarios para crear, obtener, actualizar y eliminar referencias.
 */
@RequestMapping(ApiVersion.V3 + "/references")
@RestController
public class ReferenceCrudController {

    private final ReferenceCrudService referenceCrudService;
    private final MessageService messageService;

    /**
     * Constructor para inicializar el controlador con los servicios necesarios.
     *
     * @param referenceCrudService Servicio para gestionar las operaciones CRUD de referencias.
     * @param messageService Servicio para la gestión de mensajes.
     */
    public ReferenceCrudController(ReferenceCrudService referenceCrudService, MessageService messageService) {
        this.referenceCrudService = referenceCrudService;
        this.messageService = messageService;
    }

    /**
     * Crea una nueva referencia.
     *
     * @param request DTO con los datos necesarios para crear la referencia.
     * @return Respuesta con el estado de la operación y la referencia creada.
     */
    @PostMapping
    public ResponseEntity<ApiCustomResponse<ReferenceCrudResponseDto>> create(
            @Validated @RequestBody ReferenceCrudRequestDto request
    ) {
        ReferenceCrudResponseDto reference = referenceCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(
                HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(),
                messageService.getMessage("reference.controller.create.successfully"), reference));
    }

    /**
     * Obtiene una referencia por su ID.
     *
     * @param id El ID de la referencia a obtener.
     * @return Respuesta con el estado de la operación y la referencia solicitada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<ReferenceCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        ReferenceCrudResponseDto reference = referenceCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(
                HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(),
                messageService.getMessage("reference.controller.get.successfully"), reference));
    }

    /**
     * Obtiene todas las referencias con paginación.
     *
     * @param offset El número de página para la paginación (por defecto 0).
     * @param limit El número máximo de resultados por página (por defecto 10).
     * @return Respuesta con el estado de la operación y una lista paginada de referencias.
     */
    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<ReferenceCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<ReferenceCrudResponseDto> reference = referenceCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(
                HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(),
                messageService.getMessage("reference.controller.getAll.successfully"), reference));
    }

    /**
     * Actualiza una referencia existente.
     *
     * @param request DTO con los datos de la referencia a actualizar.
     * @return Respuesta con el estado de la operación y la referencia actualizada.
     */
    @PutMapping
    public ResponseEntity<ApiCustomResponse<ReferenceCrudResponseDto>> update(
            @Validated @RequestBody ReferenceCrudUpdateRequestDto request
    ) {
        ReferenceCrudResponseDto reference = referenceCrudService.update(request);
        ApiCustomResponse<ReferenceCrudResponseDto> response = new ApiCustomResponse<>(
                "Success", HttpStatus.OK.value(),
                messageService.getMessage("reference.controller.update.successfully"), reference);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una referencia por su ID.
     *
     * @param id El ID de la referencia a eliminar.
     * @return Respuesta con el estado de la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        referenceCrudService.delete(id);
        return ResponseEntity.ok(new ApiCustomResponse<>(
                HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(),
                messageService.getMessage("reference.controller.delete.successfully"), null));
    }
}
