package com.sied.clients.service.client;

import com.sied.clients.entity.client.Client;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.client.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientValidationService {
    private final ClientRepository clientRepository;

    public ClientValidationService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client validateClientExists(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + id + " does not exist."));
    }
}