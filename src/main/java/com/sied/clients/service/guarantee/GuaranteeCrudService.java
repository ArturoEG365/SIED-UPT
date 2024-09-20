package com.sied.clients.service.guarantee;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.guarantee.request.GuaranteeCrudRequestDto;
import com.sied.clients.dto.guarantee.request.GuaranteeCrudUpdateRequestDto;
import com.sied.clients.dto.guarantee.response.GuaranteeCrudResponseDto;
import java.util.concurrent.CompletableFuture;


public interface GuaranteeCrudService {
    CompletableFuture<GuaranteeCrudResponseDto> create(GuaranteeCrudRequestDto request);

    PaginatedResponse<GuaranteeCrudResponseDto> getAll(int offset, int limit);

    GuaranteeCrudResponseDto get(Long id);

    GuaranteeCrudResponseDto update(GuaranteeCrudUpdateRequestDto request);

    void delete(Long id);
}