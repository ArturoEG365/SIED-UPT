package com.sied.clients.exceptions.global;

import com.sied.clients.exceptions.crud.CrudException;

public class EntityCreationException extends CrudException {
    public EntityCreationException(String message) {
        super(message);
    }

    public EntityCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

