package com.islamictree.start.services;

import com.islamictree.start.models.Author;
import com.islamictree.start.repositories.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll()
            .doOnError(error -> log.error("*** Error getting all users: {}"));
    }

    public Mono<Author> getAuthorById(Long id) {
        return authorRepository.findById(id)
            .switchIfEmpty(Mono.defer(Mono::empty))
            .doOnError(error -> log.error("*** Error getting author by id: {}", id));
    }

    public Mono<Author> saveAuthor(Author author) {
        return authorRepository.save(author)
            .doOnError(error -> log.error("*** Error saving author: {}", author));
    }

    public Mono<Author> updateAuthor(Author author) {
        return authorRepository.findById(author.getId())
            .switchIfEmpty(Mono.defer(Mono::empty))
            .flatMap(existing -> authorRepository.save(author))
            .doOnError(error -> log.error("*** Error updating author: {}", author));
    }

    public Mono<Void> deleteAuthor(Long id) {
        return authorRepository.deleteById(id)
            .doOnError(error -> log.error("*** Error deleting author: {}", id));
    }
}
