package com.sied.clients.controller.relatedPep;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.relatedPep.request.RelatedPepCrudRequestDto;
import com.sied.clients.dto.relatedPep.request.RelatedPepCrudUpdateRequestDto;
import com.sied.clients.dto.relatedPep.response.RelatedPepCrudResponseDto;
import com.sied.clients.service.relatedPep.RelatedPepCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequestMapping(ApiVersion.V3 + "/related_pep")
@RestController
public class RelatedPepCrudController {
    private final RelatedPepCrudService relatedPepCrudService;
    private final MessageService messageService;

    public RelatedPepCrudController(RelatedPepCrudService relatedPepCrudService, MessageService messageService) {
        this.relatedPepCrudService = relatedPepCrudService;
        this.messageService = messageService;
    }


    @PostMapping
    public ResponseEntity<ApiCustomResponse<CompletableFuture<RelatedPepCrudResponseDto>>> create(
            @Validated @RequestBody RelatedPepCrudRequestDto request
    ) {
        CompletableFuture<RelatedPepCrudResponseDto> relatedPep = relatedPepCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("relatedPep.controller.create.successfully"), relatedPep));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<CompletableFuture<RelatedPepCrudResponseDto>>> get(
            @PathVariable Long id
    ) {
        CompletableFuture<RelatedPepCrudResponseDto> relatedPep = relatedPepCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("relatedPep.controller.get.successfully"), relatedPep));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<CompletableFuture<PaginatedResponse<RelatedPepCrudResponseDto>>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        CompletableFuture<PaginatedResponse<RelatedPepCrudResponseDto>> relatedPep = relatedPepCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("relatedPep.controller.getAll.successfully"), relatedPep));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<CompletableFuture<RelatedPepCrudResponseDto>>> update(
            @Validated @RequestBody RelatedPepCrudUpdateRequestDto request
    ) {
        CompletableFuture<RelatedPepCrudResponseDto> relatedPep = relatedPepCrudService.update(request);
        ApiCustomResponse<CompletableFuture<RelatedPepCrudResponseDto>> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("relatedPep.controller.update.successfully"), relatedPep);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        relatedPepCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("relatedPep.controller.delete.successfully")));
    }
}