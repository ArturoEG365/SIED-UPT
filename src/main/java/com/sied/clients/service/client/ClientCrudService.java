package com.sied.clients.service.client;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.client.request.ClientCrudUpdateRequestDto;
import com.sied.clients.dto.client.request.ClientCrudRequestDto;
import com.sied.clients.dto.client.response.ClientCrudResponseDto;
import java.util.concurrent.CompletableFuture;

public interface ClientCrudService {
    CompletableFuture<ClientCrudResponseDto> create(ClientCrudRequestDto request);

    CompletableFuture<PaginatedResponse<ClientCrudResponseDto>> getAll(int offset, int limit);

    CompletableFuture<ClientCrudResponseDto> get(Long id);

    CompletableFuture<ClientCrudResponseDto> update(ClientCrudUpdateRequestDto request);

    CompletableFuture<Void> delete(Long id);
}
