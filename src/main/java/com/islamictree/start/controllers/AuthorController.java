package com.islamictree.start.controllers;

import com.islamictree.start.converters.AuthorDtoToAuthorConverter;
import com.islamictree.start.converters.AuthorToAuthorDtoConverter;
import com.islamictree.start.converters.AuthorWithTagsToAuthorDtoConverter;
import com.islamictree.start.dto.AuthorDto;
import com.islamictree.start.dto.TagDto;
import com.islamictree.start.models.Author;
import com.islamictree.start.services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/authors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorToAuthorDtoConverter dtoConverter;
    private final AuthorDtoToAuthorConverter authorConverter;
    private final AuthorWithTagsToAuthorDtoConverter authorWithTagsConverter;

    public AuthorController(AuthorService authorService,
                            AuthorToAuthorDtoConverter dtoConverter,
                            AuthorDtoToAuthorConverter authorConverter,
                            AuthorWithTagsToAuthorDtoConverter authorWithTagsConverter) {
        this.authorService = authorService;
        this.dtoConverter = dtoConverter;
        this.authorConverter = authorConverter;
        this.authorWithTagsConverter = authorWithTagsConverter;
    }

    @GetMapping({"", "/"})
    public Flux<AuthorDto> getAllAuthors(@RequestParam(defaultValue = "false") boolean includeTags) {
        log.info("Controller - Getting all authors (includeTags: {})", includeTags);

        if (includeTags) {
            return authorService.getAllAuthorsWithTagsBatch()
                .doOnError(error -> log.error("Error in getting all authors with tags", error))
                .map(authorWithTagsConverter::convert)
                .switchIfEmpty(Flux.defer(() -> {
                    log.error("Controller - No Authors found in database");
                    return Flux.error(new RuntimeException("No Authors found in database"));
                }))
                .doOnComplete(() -> log.info("Controller - return all authors with tags - task completed"));
        } else {
            return authorService.getAllAuthors()
                .doOnError(error -> log.error("Error in getting all authors", error))
                .map(dtoConverter::convert)
                .switchIfEmpty(Flux.defer(() -> {
                    log.error("Controller - No Authors found in database");
                    return Flux.error(new RuntimeException("No Authors found in database"));
                }))
                .doOnComplete(() -> log.info("Controller - return all authors - task completed"));
        }


    }

    @GetMapping("/{id}")
    public Mono<AuthorDto> getAuthorById(@PathVariable("id") Long id,
                                         @RequestParam(defaultValue = "false") boolean includeTags) {
        log.info("Controller - Getting author with id: {} (includeTags: {})", id, includeTags);

        if (includeTags) {
            return authorService.getAuthorWithTagsById(id)
                .doOnError(error ->
                        log.error("Error in getting author with tags with id: {} - {}", id, error.getMessage()))
                .map(authorWithTagsConverter::convert)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Controller - Author not found with id: {}", id);
                    return Mono.error(new RuntimeException("Author not found with id: " + id));
                }))
                .doOnSuccess(author ->
                        log.info("Controller - returned author with tags with id: {}", author.getId()));
        } else {
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


    }

    @PostMapping({"", "/"})
    public Mono<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        log.info("Controller - Saving author");

        Author author = authorConverter.convert(authorDto);

        if (authorDto.getTags() != null && !authorDto.getTags().isEmpty()) {
            List<Long> tagIds = authorDto.getTags().stream()
                .map(TagDto::getId)
                .collect(Collectors.toList());

            return authorService.saveAuthorWithTags(author, tagIds)
                .map(authorWithTagsConverter::convert)
                .doOnError(error ->
                    log.error("Error creating author with tags: {}", error.getMessage()));
        } else {
            return authorService.saveAuthor(author)
                .map(dtoConverter::convert)
                .doOnError(error ->
                    log.error("Error creating author: {}", error.getMessage()));
        }
    }

    @PutMapping({"", "/"})
    public Mono<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto) {
        if (authorDto.getId() == null) {
            return Mono.<AuthorDto>error(new RuntimeException("Author id is required"));
        }

        log.info("Controller - Updating author with id: {}", authorDto.getId());

        Author author = authorConverter.convert(authorDto);

        if (authorDto.getTags() != null && !authorDto.getTags().isEmpty()) {
            List<Long> tagIds = authorDto.getTags().stream()
                .map(TagDto::getId)
                .collect(Collectors.toList());

            return authorService.updateAuthorWithTags(author, tagIds)
                .map(authorWithTagsConverter::convert)
                .doOnError(error ->
                    log.error("Error updating author with tags with id {}: {}",
                        authorDto.getId(), error.getMessage()))
                .doOnSuccess(updated ->
                    log.info("Controller - update successful: {}", updated));
        } else {
            return authorService.updateAuthor(author)
                .map(dtoConverter::convert)
                .doOnError(error ->
                    log.error("Error in updating author with id {}: {}",
                        authorDto.getId(), error.getMessage()))
                .doOnSuccess(updated ->
                    log.info("Controller - update successful: {}", updated));
        }
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
