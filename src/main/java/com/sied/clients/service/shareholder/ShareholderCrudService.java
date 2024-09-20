package com.sied.clients.service.shareholder;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.shareholder.request.ShareholderCrudRequestDto;
import com.sied.clients.dto.shareholder.request.ShareholderCrudUpdateRequestDto;
import com.sied.clients.dto.shareholder.response.ShareholderCrudResponseDto;

public interface ShareholderCrudService {
    ShareholderCrudResponseDto create(ShareholderCrudRequestDto request);

    PaginatedResponse<ShareholderCrudResponseDto> getAll(int offset, int limit);

    ShareholderCrudResponseDto get(Long id);

    ShareholderCrudResponseDto update(ShareholderCrudUpdateRequestDto request);

    void delete(Long id);
}