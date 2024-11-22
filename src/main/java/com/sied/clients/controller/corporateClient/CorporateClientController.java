package com.sied.clients.controller.corporateClient;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.corporateClient.request.CorporateClientCrudRequestDto;
import com.sied.clients.dto.corporateClient.request.CorporateClientCrudUpdateRequestDto;
import com.sied.clients.dto.corporateClient.response.CorporateClientCrudResponseDto;
import com.sied.clients.service.corporateClient.CorporateClientCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;

/**
 * REST controller for managing Corporate Client operations.
 * Provides endpoints for creating, retrieving, updating, and deleting corporate clients.
 */
@RequestMapping(ApiVersion.V3 + "/corporate_clients")
@RestController
public class CorporateClientController {

    private final CorporateClientCrudService corporateClientCrudService;
    private final MessageService messageService;

    /**
     * Constructor to initialize the controller with required services.
     *
     * @param corporateClientService the service to handle corporate client CRUD operations
     * @param messageService         the service for retrieving localized messages
     */
    public CorporateClientController(CorporateClientCrudService corporateClientService, MessageService messageService) {
        this.corporateClientCrudService = corporateClientService;
        this.messageService = messageService;
    }

    /**
     * Creates a new corporate client.
     *
     * @param request the corporate client data to create
     * @return a CompletableFuture containing the created corporate client wrapped in a custom API response
     */
    @PostMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<CorporateClientCrudResponseDto>>> create(
            @Validated @RequestBody CorporateClientCrudRequestDto request
    ) {
        return corporateClientCrudService.create(request).thenApply(corporateClient ->
                ResponseEntity.status(HttpStatus.CREATED).body(
                        new ApiCustomResponse<>(
                                HttpStatus.CREATED.getReasonPhrase(),
                                HttpStatus.CREATED.value(),
                                messageService.getMessage("corporateClient.controller.create.successfully"),
                                corporateClient
                        )
                )
        );
    }

    /**
     * Retrieves a corporate client by its ID.
     *
     * @param id the ID of the corporate client to retrieve
     * @return a CompletableFuture containing the retrieved corporate client wrapped in a custom API response
     */
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ApiCustomResponse<CorporateClientCrudResponseDto>>> get(
            @PathVariable Long id
    ) {
        return corporateClientCrudService.get(id).thenApply(corporateClient ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("corporateClient.controller.get.successfully"),
                                corporateClient
                        )
                )
        );
    }

    /**
     * Retrieves a paginated list of corporate clients.
     *
     * @param offset the starting index for pagination
     * @param limit  the maximum number of items to return
     * @return a CompletableFuture containing the paginated list of corporate clients wrapped in a custom API response
     */
    @GetMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<PaginatedResponse<CorporateClientCrudResponseDto>>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return corporateClientCrudService.getAll(offset, limit).thenApply(corporateClients ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("corporateClient.controller.getAll.successfully"),
                                corporateClients
                        )
                )
        );
    }

    /**
     * Updates an existing corporate client.
     *
     * @param request the updated corporate client data
     * @return a CompletableFuture containing the updated corporate client wrapped in a custom API response
     */
    @PutMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<CorporateClientCrudResponseDto>>> update(
            @Validated @RequestBody CorporateClientCrudUpdateRequestDto request
    ) {
        return corporateClientCrudService.update(request).thenApply(corporateClient ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                "Success",
                                HttpStatus.OK.value(),
                                messageService.getMessage("corporateClient.controller.update.successfully"),
                                corporateClient
                        )
                )
        );
    }

    /**
     * Deletes a corporate client by its ID.
     *
     * @param id the ID of the corporate client to delete
     * @return a CompletableFuture containing a success message wrapped in a custom API response
     */
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<ApiCustomResponse<Void>>> delete(
            @PathVariable Long id
    ) {
        return corporateClientCrudService.delete(id).thenApply(voidResult ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("corporateClient.controller.delete.successfully")
                        )
                )
        );
    }
}
