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
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<ResponseEntity<ApiCustomResponse<BoardOfDirectorCrudResponseDto>>> create(
            @Validated @RequestBody BoardOfDirectorCrudRequestDto request
    ) {
        return boardOfDirectorCrudService.create(request).thenApply(boardOfDirector ->
                ResponseEntity.status(HttpStatus.CREATED).body(
                        new ApiCustomResponse<>(
                                HttpStatus.CREATED.getReasonPhrase(),
                                HttpStatus.CREATED.value(),
                                messageService.getMessage("boardOfDirector.controller.create.successfully"),
                                boardOfDirector
                        )
                )
        );
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ApiCustomResponse<BoardOfDirectorCrudResponseDto>>> get(
            @PathVariable Long id
    ) {
        return boardOfDirectorCrudService.get(id).thenApply(boardOfDirector ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("boardOfDirector.controller.get.successfully"),
                                boardOfDirector
                        )
                )
        );
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<PaginatedResponse<BoardOfDirectorCrudResponseDto>>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return boardOfDirectorCrudService.getAll(offset, limit).thenApply(boardOfDirectors ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("boardOfDirector.controller.getAll.successfully"),
                                boardOfDirectors
                        )
                )
        );
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<ApiCustomResponse<BoardOfDirectorCrudResponseDto>>> update(
            @Validated @RequestBody BoardOfDirectorCrudUpdateRequestDto request
    ) {
        return boardOfDirectorCrudService.update(request).thenApply(boardOfDirector ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                "Success",
                                HttpStatus.OK.value(),
                                messageService.getMessage("boardOfDirector.controller.update.successfully"),
                                boardOfDirector
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<ApiCustomResponse<Void>>> delete(
            @PathVariable Long id
    ) {
        return boardOfDirectorCrudService.delete(id).thenApply(voidResult ->
                ResponseEntity.ok(
                        new ApiCustomResponse<>(
                                HttpStatus.OK.getReasonPhrase(),
                                HttpStatus.OK.value(),
                                messageService.getMessage("boardOfDirector.controller.delete.successfully")
                        )
                )
        );
    }
}
