package com.sied.clients.controller.guarantee;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.guarantee.request.GuaranteeCrudRequestDto;
import com.sied.clients.dto.guarantee.request.GuaranteeCrudUpdateRequestDto;
import com.sied.clients.dto.guarantee.response.GuaranteeCrudResponseDto;
import com.sied.clients.service.guarantee.GuaranteeCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;


@RequestMapping(ApiVersion.V3 + "/guarantees")
@RestController
public class GuaranteeCrudController {
    private final GuaranteeCrudService guaranteeCrudService;
    private final MessageService messageService;

    public GuaranteeCrudController(GuaranteeCrudService guaranteeCrudService, MessageService messageService) {
        this.guaranteeCrudService = guaranteeCrudService;
        this.messageService = messageService;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<GuaranteeCrudResponseDto>>> create(
            @Validated @RequestBody GuaranteeCrudRequestDto request
    ) {
        return guaranteeCrudService.create(request).thenApply(guarantee ->
                        ResponseEntity.status(HttpStatus.CREATED).body(
                                new ApiCustomResponse<>(
                                        HttpStatus.CREATED.getReasonPhrase(),
                                        HttpStatus.CREATED.value(),
                                        messageService.getMessage("guarantee.controller.create.successfully"),
                                        guarantee
                                )
                        )
                );
}

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<GuaranteeCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        GuaranteeCrudResponseDto guarantee = guaranteeCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("guarantee.controller.get.successfully"), guarantee));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<GuaranteeCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<GuaranteeCrudResponseDto> guarantee = guaranteeCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("guarantee.controller.getAll.successfully"), guarantee));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<GuaranteeCrudResponseDto>> update(
            @Validated @RequestBody GuaranteeCrudUpdateRequestDto request
    ) {
        GuaranteeCrudResponseDto guarantee = guaranteeCrudService.update(request);
        ApiCustomResponse<GuaranteeCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("guarantee.controller.update.successfully"), guarantee);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        guaranteeCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("guarantee.controller.delete.successfully")));
    }
}