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

@RequestMapping(ApiVersion.V3 +"/corporate-structure")
@RestController
public class CorporateStructureController {
    private final CorporateStructureCrudService corporateStructureCrudService;
    private final MessageService messageService;

    public CorporateStructureController(CorporateStructureCrudService corporateStructureCrudService, MessageService messageService) {
        this.corporateStructureCrudService = corporateStructureCrudService;
        this.messageService = messageService;
    }

    public ResponseEntity<ApiCustomResponse<CorporateStructureCrudResponseDto>> create(
            @Validated @RequestBody CorporateStructureCrudRequestDto request
    ) {
        CorporateStructureCrudResponseDto corporateStructure = corporateStructureCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("corporateStructure.controller.create.successfully"), corporateStructure));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<CorporateStructureCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        CorporateStructureCrudResponseDto corporateStructure = corporateStructureCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("corporateStructure.controller.get.successfully"), corporateStructure));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<CorporateStructureCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<CorporateStructureCrudResponseDto> corporateStructure = corporateStructureCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("corporateStructure.controller.getAll.successfully"), corporateStructure));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<CorporateStructureCrudResponseDto>> update(
            @Validated @RequestBody CorporateStructureCrudUpdateRequestDto request
    ) {
        CorporateStructureCrudResponseDto corporateStructure = corporateStructureCrudService.update(request);
        ApiCustomResponse<CorporateStructureCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("corporateStructure.controller.update.successfully"), corporateStructure);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        corporateStructureCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("corporateStructure.controller.delete.successfully")));
    }
}