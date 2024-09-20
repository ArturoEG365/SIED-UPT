package com.sied.clients.controller.address;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.address.request.AddressCrudUpdateRequestDto;
import com.sied.clients.dto.address.request.AddressCrudRequestDto;
import com.sied.clients.dto.address.response.AddressCrudResponseDto;
import com.sied.clients.service.address.AddressCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiVersion.V3 + "/addresses")
@RestController

public class AddressCrudController {
    private final AddressCrudService addressCrudService;
    private final MessageService messageService;

    public AddressCrudController(AddressCrudService addressCrudService, MessageService messageService) {
        this.addressCrudService = addressCrudService;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ApiCustomResponse<AddressCrudResponseDto>> create(
            @Validated @RequestBody AddressCrudRequestDto request
    ) {
        AddressCrudResponseDto address = addressCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("address.controller.create.successfully"), address));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<AddressCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        AddressCrudResponseDto address = addressCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("address.controller.get.successfully"), address));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<AddressCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<AddressCrudResponseDto> address = addressCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("address.controller.getAll.successfully"), address));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<AddressCrudResponseDto>> update(
            @Validated @RequestBody AddressCrudUpdateRequestDto request
    ) {
        AddressCrudResponseDto address = addressCrudService.update(request);
        ApiCustomResponse<AddressCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("address.controller.update.successfully"), address);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        addressCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("address.controller.delete.successfully")));
    }
}