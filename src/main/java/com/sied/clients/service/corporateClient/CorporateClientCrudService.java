package com.sied.clients.service.corporateClient;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.corporateClient.request.CorporateClientCrudRequestDto;
import com.sied.clients.dto.corporateClient.request.CorporateClientCrudUpdateRequestDto;
import com.sied.clients.dto.corporateClient.response.CorporateClientCrudResponseDto;

public interface CorporateClientCrudService {
    CorporateClientCrudResponseDto create(CorporateClientCrudRequestDto request);

    PaginatedResponse<CorporateClientCrudResponseDto> getAll(int offset, int limit);

    CorporateClientCrudResponseDto get(Long id);

    CorporateClientCrudResponseDto update(CorporateClientCrudUpdateRequestDto request);

    void delete(Long id);
}
