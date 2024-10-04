package com.sied.clients.service.shareholder;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.shareholder.request.ShareholderCrudRequestDto;
import com.sied.clients.dto.shareholder.request.ShareholderCrudUpdateRequestDto;
import com.sied.clients.dto.shareholder.response.ShareholderCrudResponseDto;
import java.util.concurrent.CompletableFuture;

public interface ShareholderCrudService {
    CompletableFuture<ShareholderCrudResponseDto> create(ShareholderCrudRequestDto request);

    CompletableFuture<PaginatedResponse<ShareholderCrudResponseDto>> getAll(int offset, int limit);

    CompletableFuture<ShareholderCrudResponseDto> get(Long id);

    CompletableFuture<ShareholderCrudResponseDto> update(ShareholderCrudUpdateRequestDto request);

    CompletableFuture<Void> delete(Long id);
}
