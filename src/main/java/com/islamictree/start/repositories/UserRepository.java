package com.islamictree.start.repositories;

import com.islamictree.start.models.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
    Flux<User> findByAddressId(Long addressId);

    Mono<User> findByEmail(String email);
}
