package com.sied.clients.service.address;

import com.sied.clients.entity.address.Address;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.address.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressValidationService {
    private final AddressRepository addressRepository;

    public AddressValidationService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address validateAddressExists(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address with id " + id + " does not exist."));
    }
}