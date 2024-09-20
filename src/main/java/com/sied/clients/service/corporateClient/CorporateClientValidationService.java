package com.sied.clients.service.corporateClient;

import com.sied.clients.entity.corporateClient.CorporateClient;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.corporateClient.CorporateClientRepository;
import org.springframework.stereotype.Service;

@Service
public class CorporateClientValidationService {
    private final CorporateClientRepository corporateClientRepository;

    public CorporateClientValidationService(CorporateClientRepository corporateClientRepository) {
        this.corporateClientRepository = corporateClientRepository;
    }

    public CorporateClient validateCorporateClientExists(Long id) {
        return corporateClientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CorporateClient with id " + id + " does not exist."));
    }
}