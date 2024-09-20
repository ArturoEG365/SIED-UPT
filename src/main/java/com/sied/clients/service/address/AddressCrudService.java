package com.sied.clients.service.address;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.address.request.AddressCrudUpdateRequestDto;
import com.sied.clients.dto.address.request.AddressCrudRequestDto;
import com.sied.clients.dto.address.response.AddressCrudResponseDto;

public interface AddressCrudService {

    AddressCrudResponseDto create(AddressCrudRequestDto request);

    PaginatedResponse<AddressCrudResponseDto> getAll(int offset, int limit);

    AddressCrudResponseDto get(Long id);

    AddressCrudResponseDto update(AddressCrudUpdateRequestDto request);

    void delete(Long id);
}