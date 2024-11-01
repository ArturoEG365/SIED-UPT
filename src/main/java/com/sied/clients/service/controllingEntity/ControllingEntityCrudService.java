package com.sied.clients.service.controllingEntity;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudRequestDto;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudUpdateRequestDto;
import com.sied.clients.dto.controllingEntity.response.ControllingEntityCrudResponseDto;
import java.util.concurrent.CompletableFuture;

public interface ControllingEntityCrudService {
    CompletableFuture<ControllingEntityCrudResponseDto> createAsync(ControllingEntityCrudRequestDto request);

    CompletableFuture<PaginatedResponse<ControllingEntityCrudResponseDto>> fetchAll(int offset, int limit);

    CompletableFuture<ControllingEntityCrudResponseDto> get(Long id);

    CompletableFuture<ControllingEntityCrudResponseDto> updateEntity(ControllingEntityCrudUpdateRequestDto request);

    CompletableFuture<Void> removeById(Long id);
}
