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

@RequestMapping (ApiVersion.V3 + "/references")
@RestController
public class ReferenceCrudController {
    private final ReferenceCrudService referenceCrudService;
    private final MessageService messageService;

    public ReferenceCrudController(ReferenceCrudService referenceCrudService, MessageService messageService) {
        this.referenceCrudService = referenceCrudService;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ApiCustomResponse<ReferenceCrudResponseDto>> create(
            @Validated @RequestBody ReferenceCrudRequestDto request
    ) {
        ReferenceCrudResponseDto reference = referenceCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("reference.controller.create.successfully"), reference));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<ReferenceCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        ReferenceCrudResponseDto reference = referenceCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("reference.controller.get.successfully"), reference));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<ReferenceCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<ReferenceCrudResponseDto> reference = referenceCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("reference.controller.getAll.successfully"), reference));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<ReferenceCrudResponseDto>> update(
            @Validated @RequestBody ReferenceCrudUpdateRequestDto request
    ) {
        ReferenceCrudResponseDto reference = referenceCrudService.update(request);
        ApiCustomResponse<ReferenceCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("reference.controller.update.successfully"), reference);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        referenceCrudService.delete(id);
        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("reference.controller.delete.successfully"), null));
    }
}