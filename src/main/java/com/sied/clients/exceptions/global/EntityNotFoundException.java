package com.sied.clients.exceptions.global;

import com.sied.clients.exceptions.crud.CrudException;

public class EntityNotFoundException extends CrudException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
