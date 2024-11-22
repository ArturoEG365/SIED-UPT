package com.sied.clients.service.corporateClient;

import com.sied.clients.base.responses.PaginatedResponse;
import com.sied.clients.dto.corporateClient.request.CorporateClientCrudRequestDto;
import com.sied.clients.dto.corporateClient.request.CorporateClientCrudUpdateRequestDto;
import com.sied.clients.dto.corporateClient.response.CorporateClientCrudResponseDto;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for CRUD operations on CorporateClient entities.
 * Defines asynchronous methods for creating, retrieving, updating, and deleting corporate clients.
 */
public interface CorporateClientCrudService {

    /**
     * Creates a new CorporateClient.
     *
     * @param request the DTO containing the data for the CorporateClient
     * @return a CompletableFuture containing the response DTO of the created CorporateClient
     */
    CompletableFuture<CorporateClientCrudResponseDto> create(CorporateClientCrudRequestDto request);

    /**
     * Retrieves all CorporateClients in a paginated format.
     *
     * @param offset the starting index for pagination
     * @param limit  the maximum number of items per page
     * @return a CompletableFuture containing the paginated response DTO
     */
    CompletableFuture<PaginatedResponse<CorporateClientCrudResponseDto>> getAll(int offset, int limit);

    /**
     * Retrieves a CorporateClient by its ID.
     *
     * @param id the ID of the CorporateClient
     * @return a CompletableFuture containing the response DTO of the retrieved CorporateClient
     */
    CompletableFuture<CorporateClientCrudResponseDto> get(Long id);

    /**
     * Updates an existing CorporateClient.
     *
     * @param request the DTO containing the updated data for the CorporateClient
     * @return a CompletableFuture containing the response DTO of the updated CorporateClient
     */
    CompletableFuture<CorporateClientCrudResponseDto> update(CorporateClientCrudUpdateRequestDto request);

    /**
     * Deletes a CorporateClient by its ID.
     *
     * @param id the ID of the CorporateClient to delete
     * @return a CompletableFuture that completes when the deletion is successful
     */
    CompletableFuture<Void> delete(Long id);
}
