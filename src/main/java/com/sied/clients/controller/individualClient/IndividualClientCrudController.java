package com.sied.clients.controller.individualClient;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.individualClient.request.IndividualClientCrudRequestDto;
import com.sied.clients.dto.individualClient.request.IndividualClientCrudUpdateRequestDto;
import com.sied.clients.dto.individualClient.response.IndividualClientCrudResponseDto;
import com.sied.clients.service.individualClient.IndividualClientCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiVersion.V3 + "/individual_clients")
@RestController
public class IndividualClientCrudController {
    private final IndividualClientCrudService individualClientCrudService;
    private final MessageService messageService;

    public IndividualClientCrudController(IndividualClientCrudService individualClientCrudService, MessageService messageService) {
        this.individualClientCrudService = individualClientCrudService;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ApiCustomResponse<IndividualClientCrudResponseDto>> create(
            @Validated @RequestBody IndividualClientCrudRequestDto request
    ) {
        IndividualClientCrudResponseDto individualClient = individualClientCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("individualClient.controller.create.successfully"), individualClient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<IndividualClientCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        IndividualClientCrudResponseDto individualClient = individualClientCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("individualClient.controller.get.successfully"), individualClient));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<IndividualClientCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<IndividualClientCrudResponseDto> individualClient = individualClientCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("individualClient.controller.getAll.successfully"), individualClient));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<IndividualClientCrudResponseDto>> update(
            @Validated @RequestBody IndividualClientCrudUpdateRequestDto request
    ) {
        IndividualClientCrudResponseDto individualClient = individualClientCrudService.update(request);
        ApiCustomResponse<IndividualClientCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("individualClient.controller.update.successfully"), individualClient);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        individualClientCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("individualClient.controller.delete.successfully")));
    }
}