package com.islamictree.start.controllers;

import com.islamictree.start.converters.AddressToAddressDtoConverter;
import com.islamictree.start.dto.AddressDto;
import com.islamictree.start.services.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {

    private final MediaType mediaType = MediaType.APPLICATION_JSON;
    private final AddressService addressService;
    private final AddressToAddressDtoConverter dtoConverter;

    public AddressController(AddressService addressService, AddressToAddressDtoConverter dtoConverter) {
        this.addressService = addressService;
        this.dtoConverter = dtoConverter;
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
}
