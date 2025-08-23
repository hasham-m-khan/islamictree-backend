package com.islamictree.start.services;

import com.islamictree.start.models.User;
import com.islamictree.start.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> saveOrUpdateUser(User user) {
        log.info("*** Saving or updating user: {}", user);
        log.info("*** Checking for duplicate email.");

        return checkForDuplicateEmail(user)
            .flatMap(existing -> {
               log.info("*** Updating existing user: {}", existing);

               user.setId(existing.getId());
               return userRepository.save(existing);
            })
            .switchIfEmpty(Mono.defer(() -> {
                log.info("*** No duplicate found. Proceeding to save user.");
                return userRepository.save(user);
            }))
            .doOnSuccess(saved ->
                log.info("*** Successfully saved / updated user: {}", saved))
            .doOnError(error ->
                log.error("*** Error saving / updating user: {}", error));
    }

    public Flux<User> getAllUsers() {
        log.info("*** Getting all users...");

        return userRepository.findAll()
            .doOnComplete(() -> log.info("*** Complete getting all users."))
            .doOnError(error -> log.error("*** Error getting all users: {}", error));
    }

    public Mono<User> getUserById(Long id) {
        log.info("*** Getting user by id: {}", id);

        return userRepository.findById(id)
            .switchIfEmpty(Mono.defer(() -> {
                log.info("*** No user found with id: {}", id);
                return Mono.empty();
            }))
            .doOnError(error ->
                log.error("*** Error getting user by id: {}", error));
    }

    public Mono<Void> deleteUserById(Long id) {
        log.info("*** Deleting user by id: {}", id);

        return userRepository.findById(id)
            .switchIfEmpty(Mono.defer(() -> {
                log.info("*** No user found with id: {}", id);
                return Mono.empty();
            }))
            .flatMap(user -> {
                log.info("*** User found. Deleting user: {}", user.getId());
                return userRepository.delete(user);
            })
            .doOnSuccess(deleted -> {
                log.info("*** Successfully deleted user: {}", id);
            })
            .doOnError(error -> log.error("Error deleting user with id {}: {}", id, error));
    }

    private Mono<User> checkForDuplicateEmail(User user) {
        if (user.getEmail() == null || user.getPasswordHash() == null ||
                user.getFirstName() == null || user.getLastName() == null) {
            log.warn("User has null required fields, skipping duplicate check");
            return Mono.empty();
        }

        return userRepository.findByEmail(user.getEmail())
                .doOnNext(found -> log.info("Duplicate check found: {}", found))
                .doOnSuccess(result -> log.info("Duplicate check result: {}", result));
    }
}
