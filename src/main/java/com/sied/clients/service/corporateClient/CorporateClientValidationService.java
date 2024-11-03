package com.sied.clients.service.corporateClient;

import com.sied.clients.entity.corporateClient.CorporateClient;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.corporateClient.CorporateClientRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class CorporateClientValidationService {
    private final CorporateClientRepository corporateClientRepository;

    public CorporateClientValidationService(CorporateClientRepository corporateClientRepository) {
        this.corporateClientRepository = corporateClientRepository;
    }

    @Async
    public CompletableFuture<CorporateClient> validateCorporateClientExists(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            return corporateClientRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("CorporateClient with id " + id + " does not exist."));
        }).exceptionally(ex -> {
            throw new EntityNotFoundException("CorporateClient with id " + id + " not found due to: " + ex.getMessage());
        });
    }


}
