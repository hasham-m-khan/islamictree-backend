package com.islamictree.start.controllers;

import com.islamictree.start.converters.AddressDtoToAddressConverter;
import com.islamictree.start.converters.AddressToAddressDtoConverter;
import com.islamictree.start.dto.AddressDto;
import com.islamictree.start.models.Address;
import com.islamictree.start.services.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {

    private final AddressService addressService;
    private final AddressToAddressDtoConverter dtoConverter;
    private final AddressDtoToAddressConverter addressConverter;

    public AddressController(AddressService addressService, AddressToAddressDtoConverter dtoConverter,
                             AddressDtoToAddressConverter addressConverter) {
        this.addressService = addressService;
        this.dtoConverter = dtoConverter;
        this.addressConverter = addressConverter;
    }

    @GetMapping({"", "/"})
    public Flux<AddressDto> getAddresses() {
        log.info("Getting all addresses");
        return addressService.getAllAddresses()
            .doOnSubscribe(subscription -> log.info("Subscribed to AddressesService (getAllAdresses)"))
            .doOnComplete(() -> log.info("Repository query completed"))
            .doOnError(error -> log.error("Repository error: ", error))
            .switchIfEmpty(Flux.defer(() -> {
                log.warn("No address found in database");
                return Flux.empty();
            }))
            .map(address -> dtoConverter.convert(address));
    }

    @GetMapping("/{id}")
    public Mono<AddressDto> getAddressById(@PathVariable Long id) {
        log.info("Getting address with id {}", id);

        return addressService.getAddressById(id)
                .map(address -> dtoConverter.convert(address))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("No address found in database");
                    return Mono.empty();
                }));
    }

    @DeleteMapping("/{id}")
    Mono<Void> deleteAddressById(@PathVariable Long id) {
        log.info("Deleting address with id {}", id);

        return addressService.deleteAddressById(id)
            .doOnSuccess(address -> {
                log.info("Address with id {} deleted successfully.", id);
            })
            .doOnError(error ->
                log.error(
                    "Failed to delete address with id {}: {}",
                    id,
                    error.getMessage()
                )
            );
    }

    @PostMapping({"", "/"})
    public Mono<AddressDto> createAddress(@RequestBody AddressDto addressDto) {
        log.info("*** Creating new address: {}", addressDto);

        Address address = addressConverter.convert(addressDto);

        return addressService.saveAddress(address)
            .map(savedAddress -> dtoConverter.convert(savedAddress));
    }

    @PutMapping({"", "/"})
    public Mono<AddressDto> updateAddress(@RequestBody AddressDto addressDto) {
        if (addressDto.getId() == null) {
            return Mono.<AddressDto>error(new IllegalArgumentException("id is required"));
        }

        log.info("Updating address with id: {}", addressDto.getId());

        Address address = addressConverter.convert(addressDto);
        return addressService.updateAddress(address)
            .map(dtoConverter::convert)
            .doOnError(error -> log.error("Error: {}", error.getMessage()));
    }

}
