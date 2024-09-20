package com.sied.clients.service.individualClient;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.individualClient.request.IndividualClientCrudRequestDto;
import com.sied.clients.dto.individualClient.request.IndividualClientCrudUpdateRequestDto;
import com.sied.clients.dto.individualClient.response.IndividualClientCrudResponseDto;

public interface IndividualClientCrudService {
    IndividualClientCrudResponseDto create(IndividualClientCrudRequestDto request);

    PaginatedResponse<IndividualClientCrudResponseDto> getAll(int offset, int limit);

    IndividualClientCrudResponseDto get(Long id);

    IndividualClientCrudResponseDto update(IndividualClientCrudUpdateRequestDto request);

    void delete(Long id);
}
