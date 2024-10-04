package com.sied.clients.service.controllingEntity;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudRequestDto;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudUpdateRequestDto;
import com.sied.clients.dto.controllingEntity.response.ControllingEntityCrudResponseDto;
import java.util.concurrent.CompletableFuture;

public interface ControllingEntityCrudService {
    CompletableFuture<ControllingEntityCrudResponseDto> create(ControllingEntityCrudRequestDto request);

    CompletableFuture<PaginatedResponse<ControllingEntityCrudResponseDto>> getAll(int offset, int limit);

    CompletableFuture<ControllingEntityCrudResponseDto> get(Long id);

    CompletableFuture<ControllingEntityCrudResponseDto> update(ControllingEntityCrudUpdateRequestDto request);

    CompletableFuture<Void> delete(Long id);
}
