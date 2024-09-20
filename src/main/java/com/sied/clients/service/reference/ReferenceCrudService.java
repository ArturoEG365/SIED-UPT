package com.sied.clients.service.reference;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.reference.request.ReferenceCrudRequestDto;
import com.sied.clients.dto.reference.request.ReferenceCrudUpdateRequestDto;
import com.sied.clients.dto.reference.response.ReferenceCrudResponseDto;

public interface ReferenceCrudService {

 ReferenceCrudResponseDto create(ReferenceCrudRequestDto request);

 PaginatedResponse<ReferenceCrudResponseDto> getAll(int offset, int limit);

 ReferenceCrudResponseDto get(Long id);

 ReferenceCrudResponseDto update(ReferenceCrudUpdateRequestDto request);

 void delete(Long id);
}
