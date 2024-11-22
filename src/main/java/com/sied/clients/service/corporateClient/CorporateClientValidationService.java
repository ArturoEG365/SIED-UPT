package com.sied.clients.service.corporateClient;

import com.sied.clients.entity.corporateClient.CorporateClient;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.corporateClient.CorporateClientRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

/**
 * Service class for validating Corporate Client entities.
 * Ensures that a corporate client exists before proceeding with operations.
 */
@Service
public class CorporateClientValidationService {

    private final CorporateClientRepository corporateClientRepository;

    /**
     * Constructor for CorporateClientValidationService.
     *
     * @param corporateClientRepository the repository for accessing corporate client data
     */
    public CorporateClientValidationService(CorporateClientRepository corporateClientRepository) {
        this.corporateClientRepository = corporateClientRepository;
    }

    /**
     * Validates that a corporate client exists by its ID.
     *
     * This method is asynchronous and returns a CompletableFuture.
     * If the corporate client does not exist, an {@link EntityNotFoundException} is thrown.
     *
     * @param id the ID of the corporate client to validate
     * @return a CompletableFuture containing the CorporateClient entity if it exists
     * @throws EntityNotFoundException if the corporate client is not found
     */
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
