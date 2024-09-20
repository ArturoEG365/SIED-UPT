package com.sied.clients.service.individualClient;

import com.sied.clients.entity.individualClient.IndividualClient;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.individualClient.IndividualClientRepository;
import org.springframework.stereotype.Service;

@Service
public class IndividualClientValidationService {
    private final IndividualClientRepository individualClientRepository;

    public IndividualClientValidationService(IndividualClientRepository individualClientRepository) {
        this.individualClientRepository = individualClientRepository;
    }

    public IndividualClient validateIndividualClientExists(Long id) {
        return individualClientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Individual Client with id " + id + " does not exist."));
    }
}