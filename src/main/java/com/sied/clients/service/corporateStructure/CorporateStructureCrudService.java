package com.sied.clients.service.corporateStructure;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.corporateStructure.request.CorporateStructureCrudRequestDto;
import com.sied.clients.dto.corporateStructure.request.CorporateStructureCrudUpdateRequestDto;
import com.sied.clients.dto.corporateStructure.response.CorporateStructureCrudResponseDto;

import java.util.concurrent.CompletableFuture;

public interface CorporateStructureCrudService {

    CompletableFuture<CorporateStructureCrudResponseDto> create(CorporateStructureCrudRequestDto request);

    CompletableFuture<PaginatedResponse<CorporateStructureCrudResponseDto>> getAll(int offset, int limit);

    CompletableFuture<CorporateStructureCrudResponseDto> get(Long id);

    CompletableFuture<CorporateStructureCrudResponseDto> update(CorporateStructureCrudUpdateRequestDto request);

    CompletableFuture<Void> delete(Long id);
}
