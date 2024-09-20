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
    public ResponseEntity<ApiCustomResponse<ClientCrudResponseDto>> create(
            @Validated @RequestBody ClientCrudRequestDto request
    ) {
        ClientCrudResponseDto client = clientCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("client.controller.create.successfully"), client));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<ClientCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        ClientCrudResponseDto client = clientCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("client.controller.get.successfully"), client));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<ClientCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<ClientCrudResponseDto> client = clientCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("client.controller.getAll.successfully"), client));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<ClientCrudResponseDto>> update(
            @Validated @RequestBody ClientCrudUpdateRequestDto request
    ) {
        ClientCrudResponseDto client = clientCrudService.update(request);
        ApiCustomResponse<ClientCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("client.controller.update.successfully"), client);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        clientCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("client.controller.delete.successfully")));
    }
}