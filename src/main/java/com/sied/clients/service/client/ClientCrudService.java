package com.sied.clients.service.client;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.client.request.ClientCrudUpdateRequestDto;
import com.sied.clients.dto.client.request.ClientCrudRequestDto;
import com.sied.clients.dto.client.response.ClientCrudResponseDto;

public interface ClientCrudService {
    ClientCrudResponseDto create(ClientCrudRequestDto request);

    PaginatedResponse<ClientCrudResponseDto> getAll(int offset, int limit);

    ClientCrudResponseDto get(Long id);

    ClientCrudResponseDto update(ClientCrudUpdateRequestDto request);

    void delete(Long id);
}
