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

@RequestMapping(ApiVersion.V3 + "/corporate_clients")
@RestController
public class CorporateClientController {
    private final CorporateClientCrudService corporateClientCrudService;
    private final MessageService messageService;

    public CorporateClientController(CorporateClientCrudService corporateClientService, MessageService messageService) {
        this.corporateClientCrudService = corporateClientService;
        this.messageService = messageService;
    }

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
