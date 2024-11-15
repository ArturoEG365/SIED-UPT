package com.sied.clients.service.relatedPep;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.relatedPep.request.RelatedPepCrudRequestDto;
import com.sied.clients.dto.relatedPep.request.RelatedPepCrudUpdateRequestDto;
import com.sied.clients.dto.relatedPep.response.RelatedPepCrudResponseDto;

import java.util.concurrent.CompletableFuture;

public interface RelatedPepCrudService {
    CompletableFuture<RelatedPepCrudResponseDto> create(RelatedPepCrudRequestDto request);
    CompletableFuture<PaginatedResponse<RelatedPepCrudResponseDto>> getAll(int offset, int limit);
    CompletableFuture<RelatedPepCrudResponseDto> get(Long id);
    CompletableFuture<RelatedPepCrudResponseDto> update(RelatedPepCrudUpdateRequestDto request);
    CompletableFuture<Void> delete(Long id);
}
