package com.sied.clients.controller.client;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.client.request.ClientCrudUpdateRequestDto;
import com.sied.clients.dto.client.request.ClientCrudRequestDto;
import com.sied.clients.dto.client.response.ClientCrudResponseDto;
import com.sied.clients.service.client.ClientCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(ApiVersion.V3 + "/clients")
@RestController
public class ClientCrudController {

    private final ClientCrudService clientCrudService;
    private final MessageService messageService;

    public ClientCrudController(ClientCrudService clientCrudService, MessageService messageService) {
        this.clientCrudService = clientCrudService;
        this.messageService = messageService;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<ClientCrudResponseDto>>> create(
            @Validated @RequestBody ClientCrudRequestDto request
    ) {
        return clientCrudService.create(request)
                .thenApply(client -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ApiCustomResponse<>(
                                HttpStatus.CREATED.getReasonPhrase(),
                                HttpStatus.CREATED.value(),
                                messageService.getMessage("client.controller.create.successfully"),
                                client
                        )));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ApiCustomResponse<ClientCrudResponseDto>>> get(
            @PathVariable Long id
    ) {
        return clientCrudService.get(id)
                .thenApply(client -> ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("client.controller.get.successfully"),
                                client
                        )));
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<PaginatedResponse<ClientCrudResponseDto>>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return clientCrudService.getAll(offset, limit)
                .thenApply(clientPage -> ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("client.controller.getAll.successfully"),
                                clientPage
                        )));
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<ClientCrudResponseDto>>> update(
            @Validated @RequestBody ClientCrudUpdateRequestDto request
    ) {
        return clientCrudService.update(request)
                .thenApply(client -> ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                "Success",
                                HttpStatus.OK.value(),
                                messageService.getMessage("client.controller.update.successfully"),
                                client
                        )));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<ApiCustomResponse<Void>>> delete(
            @PathVariable Long id
    ) {
        return clientCrudService.delete(id)
                .thenApply(v -> ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("client.controller.delete.successfully")
                        )));
    }
}
