package com.sied.clients.service.controllingEntity;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudRequestDto;
import com.sied.clients.dto.controllingEntity.request.ControllingEntityCrudUpdateRequestDto;
import com.sied.clients.dto.controllingEntity.response.ControllingEntityCrudResponseDto;

public interface ControllingEntityCrudService {
    ControllingEntityCrudResponseDto create(ControllingEntityCrudRequestDto request);

    PaginatedResponse<ControllingEntityCrudResponseDto> getAll(int offset, int limit);

    ControllingEntityCrudResponseDto get(Long id);

    ControllingEntityCrudResponseDto update(ControllingEntityCrudUpdateRequestDto request);

    void delete(Long id);
}
