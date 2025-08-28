package com.islamictree.start.controllers;

import com.islamictree.start.converters.AuthorDtoToAuthorConverter;
import com.islamictree.start.converters.AuthorToAuthorDtoConverter;
import com.islamictree.start.dto.AuthorDto;
import com.islamictree.start.models.Author;
import com.islamictree.start.services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/authors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorToAuthorDtoConverter dtoConverter;
    private final AuthorDtoToAuthorConverter authorConverter;

    public AuthorController(AuthorService authorService, AuthorToAuthorDtoConverter dtoConverter,
                            AuthorDtoToAuthorConverter authorConverter) {
        this.authorService = authorService;
        this.dtoConverter = dtoConverter;
        this.authorConverter = authorConverter;
    }

    @GetMapping({"", "/"})
    public Flux<AuthorDto> getAllAuthors() {
        log.info("Controller - Getting all authors");

        return authorService.getAllAuthors()
            .doOnError(error -> log.error("Error in getting all authors", error))
            .flatMap(author -> Flux.just(dtoConverter.convert(author)))
            .switchIfEmpty(Flux.defer(() -> {
                log.error("Controller - No Authors found in database");

                return Flux.error(new RuntimeException("No Authors found in database"));
            }))
            .doOnComplete(() -> log.info("Controller - return all authors - task completed"));
    }

    @GetMapping("/{id}")
    public Mono<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        log.info("Controller - Getting author with id: {}", id);

        return authorService.getAuthorById(id)
            .doOnError(error ->
                    log.error("Error in getting author with id: {} - {}", id, error.getMessage()))
            .map(dtoConverter::convert)
            .switchIfEmpty(Mono.defer(() -> {
                log.error("Controller - Author not found with id: {}", id);

                return Mono.error(new RuntimeException("User not found with id: " + id));
            }))
            .doOnSuccess(author ->
                    log.info("Controller - returned author with id: {}", author.getId()));
    }

    @PostMapping({"", "/"})
    public Mono<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        log.info("Controller - Saving author");

        Author author = authorConverter.convert(authorDto);
        return authorService.saveAuthor(author)
            .map(dtoConverter::convert)
            .doOnError(error ->
                    log.error("Error creating author: {}", error.getMessage()));
    }

    @PutMapping({"", "/"})
    public Mono<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto) {
        if (authorDto.getId() == null) {
            return Mono.<AuthorDto>error(new RuntimeException("Author id is required"));
        }

        log.info("Controller - Updating author with id: {}", authorDto.getId());

        Author author = authorConverter.convert(authorDto);
        return authorService.updateAuthor(author)
            .doOnError(error ->
                    log.error("Error in updating author with id {}: {}",
                    authorDto.getId(), error.getMessage()))
            .flatMap(updated -> Mono.just(dtoConverter.convert(updated)))
            .doOnSuccess(updated ->
                    log.info("Controller - update successful: {}", updated));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAuthor(@PathVariable("id") Long id) {
        log.info("Controller - Deleting author with id: {}", id);

        return authorService.deleteAuthor(id)
            .doOnError(error ->
                    log.error("Error in deleting author with id {}: {}",
                            id, error.getMessage()))
            .doOnSuccess(deleted -> log.info("Controller - delete successful: {}", id));
    }
}
