package com.sied.clients.controller.boardOfDirector;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.boardOfDirector.request.BoardOfDirectorCrudRequestDto;
import com.sied.clients.dto.boardOfDirector.request.BoardOfDirectorCrudUpdateRequestDto;
import com.sied.clients.dto.boardOfDirector.response.BoardOfDirectorCrudResponseDto;
import com.sied.clients.service.boardOfDirector.BoardOfDirectorCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiVersion.V3 + "/board_of_directors")
@RestController
public class BoardOfDirectorController {
    private final BoardOfDirectorCrudService boardOfDirectorCrudService;
    private final MessageService messageService;

    public BoardOfDirectorController(BoardOfDirectorCrudService boardOfDirectorCrudService, MessageService messageService) {
        this.boardOfDirectorCrudService = boardOfDirectorCrudService;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ApiCustomResponse<BoardOfDirectorCrudResponseDto>> create(
            @Validated @RequestBody BoardOfDirectorCrudRequestDto request
    ) {
        BoardOfDirectorCrudResponseDto boardOfDirector = boardOfDirectorCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("boardOfDirector.controller.create.successfully"), boardOfDirector));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<BoardOfDirectorCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        BoardOfDirectorCrudResponseDto boardOfDirector = boardOfDirectorCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("boardOfDirector.controller.get.successfully"), boardOfDirector));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<BoardOfDirectorCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<BoardOfDirectorCrudResponseDto> boardOfDirector = boardOfDirectorCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("boardOfDirector.controller.getAll.successfully"), boardOfDirector));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<BoardOfDirectorCrudResponseDto>> update(
            @Validated @RequestBody BoardOfDirectorCrudUpdateRequestDto request
    ) {
        BoardOfDirectorCrudResponseDto boardOfDirector = boardOfDirectorCrudService.update(request);
        ApiCustomResponse<BoardOfDirectorCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("boardOfDirector.controller.update.successfully"), boardOfDirector);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        boardOfDirectorCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("boardOfDirector.controller.delete.successfully")));
    }
}