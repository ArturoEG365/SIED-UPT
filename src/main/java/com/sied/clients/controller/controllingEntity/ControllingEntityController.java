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
    public ResponseEntity<ApiCustomResponse<ControllingEntityCrudResponseDto>> create(
            @Validated @RequestBody ControllingEntityCrudRequestDto request
    ) {
        ControllingEntityCrudResponseDto controllingEntity = controllingEntityCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("controllingEntity.controller.create.successfully"), controllingEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<ControllingEntityCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        ControllingEntityCrudResponseDto controllingEntity = controllingEntityCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("controllingEntity.controller.get.successfully"), controllingEntity));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<ControllingEntityCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<ControllingEntityCrudResponseDto> controllingEntity = controllingEntityCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("controllingEntity.controller.getAll.successfully"), controllingEntity));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<ControllingEntityCrudResponseDto>> update(
            @Validated @RequestBody ControllingEntityCrudUpdateRequestDto request
    ) {
        ControllingEntityCrudResponseDto controllingEntity = controllingEntityCrudService.update(request);
        ApiCustomResponse<ControllingEntityCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("controllingEntity.controller.update.successfully"), controllingEntity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        controllingEntityCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("controllingEntity.controller.delete.successfully")));
    }
}