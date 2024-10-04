package com.sied.clients.controller.corporateStructure;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.corporateStructure.request.CorporateStructureCrudRequestDto;
import com.sied.clients.dto.corporateStructure.request.CorporateStructureCrudUpdateRequestDto;
import com.sied.clients.dto.corporateStructure.response.CorporateStructureCrudResponseDto;
import com.sied.clients.service.corporateStructure.CorporateStructureCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(ApiVersion.V3 + "/corporate-structure")
@RestController
public class CorporateStructureController {
    private final CorporateStructureCrudService corporateStructureCrudService;
    private final MessageService messageService;

    public CorporateStructureController(CorporateStructureCrudService corporateStructureCrudService, MessageService messageService) {
        this.corporateStructureCrudService = corporateStructureCrudService;
        this.messageService = messageService;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<CorporateStructureCrudResponseDto>>> create(
            @Validated @RequestBody CorporateStructureCrudRequestDto request
    ) {
        return corporateStructureCrudService.create(request)
                .thenApply(corporateStructure -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ApiCustomResponse<>(
                                HttpStatus.CREATED.getReasonPhrase(),
                                HttpStatus.CREATED.value(),
                                messageService.getMessage("corporateStructure.controller.create.successfully"),
                                corporateStructure
                        )));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ApiCustomResponse<CorporateStructureCrudResponseDto>>> get(
            @PathVariable Long id
    ) {
        return corporateStructureCrudService.get(id)
                .thenApply(corporateStructure -> ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("corporateStructure.controller.get.successfully"),
                                corporateStructure
                        )));
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<PaginatedResponse<CorporateStructureCrudResponseDto>>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return corporateStructureCrudService.getAll(offset, limit)
                .thenApply(corporateStructures -> ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("corporateStructure.controller.getAll.successfully"),
                                corporateStructures
                        )));
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<CorporateStructureCrudResponseDto>>> update(
            @Validated @RequestBody CorporateStructureCrudUpdateRequestDto request
    ) {
        return corporateStructureCrudService.update(request)
                .thenApply(corporateStructure -> ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                "Success",
                                HttpStatus.OK.value(),
                                messageService.getMessage("corporateStructure.controller.update.successfully"),
                                corporateStructure
                        )));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<ApiCustomResponse<Void>>> delete(
            @PathVariable Long id
    ) {
        return corporateStructureCrudService.delete(id)
                .thenApply(aVoid -> ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("corporateStructure.controller.delete.successfully")
                        )));
    }
}
