package com.islamictree.start.repositories;

import com.islamictree.start.models.Address;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AddressRepository extends R2dbcRepository<Address, Long> {
    Flux<Address> findByStreetAndCityAndStateAndCountryAndZipCode(
            String street, String city, String state, String country, String zipCode);
}
