package com.sied.clients.service.person;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.person.request.PersonCrudUpdateRequestDto;
import com.sied.clients.dto.person.request.PersonCrudRequestDto;
import com.sied.clients.dto.person.response.PersonCrudResponseDto;

public interface PersonCrudService {
    PersonCrudResponseDto create(PersonCrudRequestDto request);

    PaginatedResponse<PersonCrudResponseDto> getAll(int offset, int limit);

    PersonCrudResponseDto get(Long id);

    PersonCrudResponseDto update(PersonCrudUpdateRequestDto request);

    void delete(Long id);
}
