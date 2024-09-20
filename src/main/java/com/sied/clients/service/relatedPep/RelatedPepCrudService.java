package com.sied.clients.service.relatedPep;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.relatedPep.request.RelatedPepCrudRequestDto;
import com.sied.clients.dto.relatedPep.request.RelatedPepCrudUpdateRequestDto;
import com.sied.clients.dto.relatedPep.response.RelatedPepCrudResponseDto;

public interface RelatedPepCrudService {
    RelatedPepCrudResponseDto create(RelatedPepCrudRequestDto request);

    PaginatedResponse<RelatedPepCrudResponseDto> getAll(int offset, int limit);

    RelatedPepCrudResponseDto get(Long id);

    RelatedPepCrudResponseDto update(RelatedPepCrudUpdateRequestDto request);

    void delete(Long id);
}
