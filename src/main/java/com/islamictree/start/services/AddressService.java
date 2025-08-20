package com.islamictree.start.services;

import com.islamictree.start.models.Address;
import com.islamictree.start.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Flux<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
}
