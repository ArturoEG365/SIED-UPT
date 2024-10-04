package com.sied.clients.service.boardOfDirector;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.boardOfDirector.request.BoardOfDirectorCrudRequestDto;
import com.sied.clients.dto.boardOfDirector.request.BoardOfDirectorCrudUpdateRequestDto;
import com.sied.clients.dto.boardOfDirector.response.BoardOfDirectorCrudResponseDto;
import java.util.concurrent.CompletableFuture;

public interface BoardOfDirectorCrudService {
    CompletableFuture<BoardOfDirectorCrudResponseDto> create(BoardOfDirectorCrudRequestDto request);

    CompletableFuture<PaginatedResponse<BoardOfDirectorCrudResponseDto>> getAll(int offset, int limit);

    CompletableFuture<BoardOfDirectorCrudResponseDto> get(Long id);

    CompletableFuture<BoardOfDirectorCrudResponseDto> update(BoardOfDirectorCrudUpdateRequestDto request);

    CompletableFuture<Void> delete(Long id);
}
