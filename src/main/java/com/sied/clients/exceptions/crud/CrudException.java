package com.sied.clients.exceptions.crud;

public class CrudException extends RuntimeException {
    public CrudException(String message) {
        super(message);
    }

    public CrudException(String message, Throwable cause) {
        super(message, cause);
    }
}