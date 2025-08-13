package com.islamictree.start.repositories;

import com.islamictree.start.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
    Flux<User> findByAddressId(Long addressId);
}
