package com.sied.clients.base.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PaginatedBaseResponse is a class that represents a paginated response for API endpoints.
 * It includes information about the total number of items and the total number of pages.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedBaseResponse {
    private long totalItems;
    private int totalPages;
}
