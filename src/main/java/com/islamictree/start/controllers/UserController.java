package com.islamictree.start.controllers;

import com.islamictree.start.converters.UserDtoToUserConverter;
import com.islamictree.start.converters.UserToUserDtoConverter;
import com.islamictree.start.dto.UserDto;
import com.islamictree.start.models.User;
import com.islamictree.start.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;
    private final UserToUserDtoConverter dtoConverter;
    private final UserDtoToUserConverter userConverter;

    public UserController(UserService userService, UserToUserDtoConverter dtoConverter,
                          UserDtoToUserConverter userConverter) {
        this.userService = userService;
        this.dtoConverter = dtoConverter;
        this.userConverter = userConverter;
    }

    @GetMapping({"", "/"})
    public Flux<UserDto> getAllUsers() {
        log.info("Controller - Getting all users");

        return userService.getAllUsers()
            .map(user -> dtoConverter.convert(user))
            .switchIfEmpty(Flux.defer(() -> {
                log.info("Controller - No users found in database.");
                return Flux.empty();
            }))
            .doOnError(error -> log.error("Repository error: ", error));
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUserById(@PathVariable("id") Long id) {
        log.info("Controller - Getting user by id {}", id);

        return userService.getUserById(id)
            .map(user -> dtoConverter.convert(user))
            .switchIfEmpty(Mono.defer(() -> {
                log.info("Controller - No user found in database.");
                return Mono.empty();
            }))
            .doOnError(error -> log.error("Repository error: ", error));
    }

    @PostMapping({"", "/"})
    public Mono<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Controller - Creating user {}", userDto);

        User user = userConverter.convert(userDto);

        return userService.saveUser(user)
            .map(savedUser -> dtoConverter.convert(savedUser))
            .doOnSuccess(newUser -> log.info("Controller - New user created: {}", newUser))
            .doOnError(error -> log.error("Repository error: ", error));
    }

    @PostMapping("/{id}")
    public Mono<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        log.info("Controller - Updating user {}", userDto);

        User user = userConverter.convert(userDto);

        return userService.updateUser(id, user)
            .map(savedUser -> dtoConverter.convert(savedUser))
            .doOnSuccess(newUser -> log.info("Controller - New user created: {}", newUser))
            .doOnError(error -> log.error("Repository error: ", error));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id) {
        log.info("Controller - Deleting user {}", id);

        return userService.deleteUserById(id)
            .doOnSuccess(deletedUser -> log.info("Controller - Deleted user with id: {}", id))
            .doOnError(error -> log.error("Repository error: ", error));
    }
}
