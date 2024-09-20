package com.sied.clients.controller.jointObligor;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.jointObligor.request.JointObligorCrudRequestDto;
import com.sied.clients.dto.jointObligor.request.JointObligorCrudUpdateRequestDto;
import com.sied.clients.dto.jointObligor.response.JointObligorCrudResponseDto;
import com.sied.clients.service.jointObligor.JointObligorCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiVersion.V3 + "/joint_obligors")
@RestController
public class JointObligorCrudController {
    private final JointObligorCrudService jointObligorCrudService;
    private final MessageService messageService;

    public JointObligorCrudController(JointObligorCrudService jointObligorCrudService, MessageService messageService) {
        this.jointObligorCrudService = jointObligorCrudService;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ApiCustomResponse<JointObligorCrudResponseDto>> create(
            @Validated @RequestBody JointObligorCrudRequestDto request
    ) {
        JointObligorCrudResponseDto jointObligor = jointObligorCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("jointObligor.controller.create.successfully"), jointObligor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<JointObligorCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        JointObligorCrudResponseDto jointObligor = jointObligorCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("jointObligor.controller.get.successfully"), jointObligor));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<JointObligorCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<JointObligorCrudResponseDto> jointObligor = jointObligorCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("jointObligor.controller.getAll.successfully"), jointObligor));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<JointObligorCrudResponseDto>> update(
            @Validated @RequestBody JointObligorCrudUpdateRequestDto request
    ) {
        JointObligorCrudResponseDto jointObligor = jointObligorCrudService.update(request);
        ApiCustomResponse<JointObligorCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("jointObligor.controller.update.successfully"), jointObligor);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        jointObligorCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("jointObligor.controller.delete.successfully")));
    }
}