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
    public ResponseEntity<ApiCustomResponse<CorporateClientCrudResponseDto>> create(
            @Validated @RequestBody CorporateClientCrudRequestDto request
    ) {
        CorporateClientCrudResponseDto corporateClient = corporateClientCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("corporateClient.controller.create.successfully"), corporateClient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<CorporateClientCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        CorporateClientCrudResponseDto corporateClient = corporateClientCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("corporateClient.controller.get.successfully"), corporateClient));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<CorporateClientCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<CorporateClientCrudResponseDto> corporateClient = corporateClientCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("corporateClient.controller.getAll.successfully"), corporateClient));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<CorporateClientCrudResponseDto>> update(
            @Validated @RequestBody CorporateClientCrudUpdateRequestDto request
    ) {
        CorporateClientCrudResponseDto corporateClient = corporateClientCrudService.update(request);
        ApiCustomResponse<CorporateClientCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("corporateClient.controller.update.successfully"), corporateClient);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        corporateClientCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("guarantee.controller.delete.successfully")));
    }
}