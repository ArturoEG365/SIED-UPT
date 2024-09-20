package com.sied.clients.controller.shareholder;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.shareholder.request.ShareholderCrudRequestDto;
import com.sied.clients.dto.shareholder.request.ShareholderCrudUpdateRequestDto;
import com.sied.clients.dto.shareholder.response.ShareholderCrudResponseDto;
import com.sied.clients.service.shareholder.ShareholderCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiVersion.V3 + "/shareholders")
@RestController
public class ShareholderCrudController {
    private final ShareholderCrudService shareholderCrudService;
    private final MessageService messageService;

    public ShareholderCrudController(ShareholderCrudService shareholderCrudService, MessageService messageService) {
        this.shareholderCrudService = shareholderCrudService;
        this.messageService = messageService;
    }

    public ResponseEntity<ApiCustomResponse<ShareholderCrudResponseDto>> create(
            @Validated @RequestBody ShareholderCrudRequestDto request
    ) {
        ShareholderCrudResponseDto shareholder = shareholderCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("shareholder.controller.create.successfully"), shareholder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<ShareholderCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        ShareholderCrudResponseDto shareholder = shareholderCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("shareholder.controller.get.successfully"), shareholder));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<ShareholderCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<ShareholderCrudResponseDto> shareholder = shareholderCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("shareholder.controller.getAll.successfully"), shareholder));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<ShareholderCrudResponseDto>> update(
            @Validated @RequestBody ShareholderCrudUpdateRequestDto request
    ) {
        ShareholderCrudResponseDto shareholder = shareholderCrudService.update(request);
        ApiCustomResponse<ShareholderCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("shareholder.controller.update.successfully"), shareholder);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        shareholderCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("shareholder.controller.delete.successfully")));
    }
}