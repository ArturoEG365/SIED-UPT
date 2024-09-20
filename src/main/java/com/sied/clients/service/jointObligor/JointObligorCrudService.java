package com.sied.clients.service.jointObligor;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.jointObligor.request.JointObligorCrudRequestDto;
import com.sied.clients.dto.jointObligor.request.JointObligorCrudUpdateRequestDto;
import com.sied.clients.dto.jointObligor.response.JointObligorCrudResponseDto;

public interface JointObligorCrudService {
    JointObligorCrudResponseDto create(JointObligorCrudRequestDto request);

    PaginatedResponse<JointObligorCrudResponseDto> getAll(int offset, int limit);

    JointObligorCrudResponseDto get(Long id);

    JointObligorCrudResponseDto update(JointObligorCrudUpdateRequestDto request);

    void delete(Long id);
}
