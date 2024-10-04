package com.sied.clients.service.corporateClient;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.corporateClient.request.CorporateClientCrudRequestDto;
import com.sied.clients.dto.corporateClient.request.CorporateClientCrudUpdateRequestDto;
import com.sied.clients.dto.corporateClient.response.CorporateClientCrudResponseDto;
import java.util.concurrent.CompletableFuture;

public interface CorporateClientCrudService {
    CompletableFuture<CorporateClientCrudResponseDto> create(CorporateClientCrudRequestDto request);

    CompletableFuture<PaginatedResponse<CorporateClientCrudResponseDto>> getAll(int offset, int limit);

    CompletableFuture<CorporateClientCrudResponseDto> get(Long id);

    CompletableFuture<CorporateClientCrudResponseDto> update(CorporateClientCrudUpdateRequestDto request);

    CompletableFuture<Void> delete(Long id);
}
