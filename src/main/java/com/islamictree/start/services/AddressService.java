package com.islamictree.start.services;

import com.islamictree.start.models.Address;
import com.islamictree.start.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public Mono<Address> saveOrUpdateAddress(Address address) {
        log.info("*** Saving or updating address: {}", address);
        log.info("*** Checking for duplicate address.");

        return checkForDuplicateAddress(address)
                .doOnNext(existing -> {
                    log.info("*** Updating existing address: {}", existing);
                    address.setId(existing.getId());
                    addressRepository.save(existing);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("*** No duplicate found, proceeding to save");
                    return Mono.empty();
                }))
                .cast(Address.class)
                .switchIfEmpty(addressRepository.save(address))
                    .doOnSuccess(saved -> log.info("Successfully saved address: {}", saved))
                .doOnError(error -> log.error("Error while saving or updating address: {}", error));
    }

    public Mono<Void> deleteAddressById(Long id) {
        return addressRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Address not found with id: " + id)))
                .doOnNext(address -> log.info("Found address to delete: {}", address))
                .flatMap(address -> addressRepository.deleteById(id))
                .doOnSuccess(result -> log.info("Successfully deleted address with id: {}", id))
                .doOnError(error -> log.error("Error deleting address with id {}: {}", id, error.getMessage()));
    }

    private Mono<Address> checkForDuplicateAddress(Address address) {
        if (address.getStreet() == null || address.getCity() == null ||
                address.getState() == null || address.getCountry() == null ||
                address.getZipCode() == null) {
            log.warn("Address has null required fields, skipping duplicate check");
            return Mono.empty();
        }


        return addressRepository.findByStreetAndCityAndStateAndCountryAndZipCode(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getZipCode()
        )
                .doOnNext(found -> log.info("Duplicate check found: {}", found))
                .next()
                .doOnSuccess(result -> log.info("Duplicate check result: {}", result));
    }

}
