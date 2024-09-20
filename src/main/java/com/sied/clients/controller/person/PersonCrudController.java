package com.sied.clients.controller.person;

import com.sied.clients.base.responses.ApiCustomResponse;
import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.config.general.ApiVersion;
import com.sied.clients.dto.person.request.PersonCrudUpdateRequestDto;
import com.sied.clients.dto.person.request.PersonCrudRequestDto;
import com.sied.clients.dto.person.response.PersonCrudResponseDto;
import com.sied.clients.service.person.PersonCrudService;
import com.sied.clients.util.security.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiVersion.V3 + "/persons")
@RestController

public class PersonCrudController {
    private final PersonCrudService personCrudService;
    private final MessageService messageService;

    public PersonCrudController(PersonCrudService personCrudService, MessageService messageService) {
        this.personCrudService = personCrudService;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ApiCustomResponse<PersonCrudResponseDto>> create(
            @Validated @RequestBody PersonCrudRequestDto request
    ) {
        PersonCrudResponseDto person = personCrudService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiCustomResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(), messageService.getMessage("person.controller.create.successfully"), person));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<PersonCrudResponseDto>> get(
            @PathVariable Long id
    ) {
        PersonCrudResponseDto person = personCrudService.get(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("person.controller.get.successfully"), person));
    }

    @GetMapping
    public ResponseEntity<ApiCustomResponse<PaginatedResponse<PersonCrudResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PaginatedResponse<PersonCrudResponseDto> person = personCrudService.getAll(offset, limit);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("person.controller.getAll.successfully"), person));
    }

    @PutMapping
    public ResponseEntity<ApiCustomResponse<PersonCrudResponseDto>> update(
            @Validated @RequestBody PersonCrudUpdateRequestDto request
    ) {
        PersonCrudResponseDto person = personCrudService.update(request);
        ApiCustomResponse<PersonCrudResponseDto> response = new ApiCustomResponse<>("Success", HttpStatus.OK.value(), messageService.getMessage("person.controller.update.successfully"), person);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<Void>> delete(
            @PathVariable Long id
    ) {
        personCrudService.delete(id);

        return ResponseEntity.ok(new ApiCustomResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), messageService.getMessage("person.controller.delete.successfully")));
    }
}