package com.sied.clients.service.boardOfDirector;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.boardOfDirector.request.BoardOfDirectorCrudRequestDto;
import com.sied.clients.dto.boardOfDirector.request.BoardOfDirectorCrudUpdateRequestDto;
import com.sied.clients.dto.boardOfDirector.response.BoardOfDirectorCrudResponseDto;

public interface BoardOfDirectorCrudService {
    BoardOfDirectorCrudResponseDto create(BoardOfDirectorCrudRequestDto request);

    PaginatedResponse<BoardOfDirectorCrudResponseDto> getAll(int offset, int limit);

    BoardOfDirectorCrudResponseDto get(Long id);

    BoardOfDirectorCrudResponseDto update(BoardOfDirectorCrudUpdateRequestDto request);

    void delete(Long id);
}