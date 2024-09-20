package com.sied.clients.base.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * PaginatedResponse is a generic class that represents a paginated response for API endpoints.
 * It extends {@link PaginatedBaseResponse} and includes a list of items of type T.
 *
 * @param <T> The type of the items included in the paginated response.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PaginatedResponse<T> extends PaginatedBaseResponse {
    private List<T> items;

    /**
     * Constructs a PaginatedResponse with the given total items, total pages, and list of items.
     *
     * @param totalItems The total number of items.
     * @param totalPages The total number of pages.
     * @param items The list of items included in the response.
     */
    public PaginatedResponse(long totalItems, int totalPages, List<T> items) {
        super(totalItems, totalPages);
        this.items = items;
    }
}
