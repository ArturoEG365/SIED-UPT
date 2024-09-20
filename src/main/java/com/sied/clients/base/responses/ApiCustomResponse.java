package com.sied.clients.base.responses;

import lombok.Data;

/**
 * ApiCustomResponse is a generic class that represents a custom response for API endpoints.
 * It includes information about the status, status code, message, data count, and the data itself.
 *
 * @param <T> The type of the data included in the response.
 */
@Data
public class ApiCustomResponse<T> {
    private String status;
    private int statusCode;
    private String message;
    private int dataCount;
    private T data;

    /**
     * Constructs an ApiCustomResponse with the given status, status code, message, and data.
     *
     * @param status The status of the response.
     * @param statusCode The HTTP status code of the response.
     * @param message A message describing the response.
     * @param data The data included in the response.
     */
    public ApiCustomResponse(String status, int statusCode, String message, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.dataCount = data != null ? 1 : 0;
    }

    /**
     * Constructs an ApiCustomResponse with the given status, status code, and message.
     * This constructor sets the data to null and data count to 0.
     *
     * @param status The status of the response.
     * @param statusCode The HTTP status code of the response.
     * @param message A message describing the response.
     */
    public ApiCustomResponse(String status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
        this.dataCount = 0;
    }
}
