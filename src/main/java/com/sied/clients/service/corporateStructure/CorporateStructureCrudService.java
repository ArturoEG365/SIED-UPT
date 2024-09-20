package com.sied.clients.service.corporateStructure;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.corporateStructure.request.CorporateStructureCrudRequestDto;
import com.sied.clients.dto.corporateStructure.request.CorporateStructureCrudUpdateRequestDto;
import com.sied.clients.dto.corporateStructure.response.CorporateStructureCrudResponseDto;

public interface CorporateStructureCrudService {

    CorporateStructureCrudResponseDto create(CorporateStructureCrudRequestDto request);

    PaginatedResponse<CorporateStructureCrudResponseDto> getAll(int offset, int limit);

    CorporateStructureCrudResponseDto get(Long id);

    CorporateStructureCrudResponseDto update(CorporateStructureCrudUpdateRequestDto request);

    void delete(Long id);
}