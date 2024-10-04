package com.sied.clients.controller.controllingEntity;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudRequestDto;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudUpdateRequestDto;
import com.sied.clients.dto.controllingEntity.response.ControllingEntityCrudResponseDto;
import com.sied.clients.service.controllingEntity.ControllingEntityCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;

@RequestMapping(ApiVersion.V3 + "/controlling_entities")
@RestController
public class ControllingEntityController {
    private final ControllingEntityCrudService controllingEntityCrudService;
    private final MessageService messageService;

    public ControllingEntityController(ControllingEntityCrudService controllingEntityCrudService, MessageService messageService) {
        this.controllingEntityCrudService = controllingEntityCrudService;
        this.messageService = messageService;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<ControllingEntityCrudResponseDto>>> create(
            @Validated @RequestBody ControllingEntityCrudRequestDto request
    ) {
        return controllingEntityCrudService.create(request).thenApply(controllingEntity ->
                ResponseEntity.status(HttpStatus.CREATED).body(
                        new ApiCustomResponse<>(
                                HttpStatus.CREATED.getReasonPhrase(),
                                HttpStatus.CREATED.value(),
                                messageService.getMessage("controllingEntity.controller.create.successfully"),
                                controllingEntity
                        )
                )
        );
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ApiCustomResponse<ControllingEntityCrudResponseDto>>> get(
            @PathVariable Long id
    ) {
        return controllingEntityCrudService.get(id).thenApply(controllingEntity ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("controllingEntity.controller.get.successfully"),
                                controllingEntity
                        )
                )
        );
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<PaginatedResponse<ControllingEntityCrudResponseDto>>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return controllingEntityCrudService.getAll(offset, limit).thenApply(controllingEntities ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("controllingEntity.controller.getAll.successfully"),
                                controllingEntities
                        )
                )
        );
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<ControllingEntityCrudResponseDto>>> update(
            @Validated @RequestBody ControllingEntityCrudUpdateRequestDto request
    ) {
        return controllingEntityCrudService.update(request).thenApply(controllingEntity ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                "Success",
                                HttpStatus.OK.value(),
                                messageService.getMessage("controllingEntity.controller.update.successfully"),
                                controllingEntity
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<ApiCustomResponse<Void>>> delete(
            @PathVariable Long id
    ) {
        return controllingEntityCrudService.delete(id).thenApply(voidResult ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("controllingEntity.controller.delete.successfully")
                        )
                )
        );
    }
}
