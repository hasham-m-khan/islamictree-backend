package com.islamictree.start.services;

import com.islamictree.start.models.Author;
import com.islamictree.start.models.AuthorTag;
import com.islamictree.start.models.Tag;
import com.islamictree.start.models.wrappers.AuthorWithTags;
import com.islamictree.start.repositories.AuthorRepository;
import com.islamictree.start.repositories.AuthorTagRepository;
import com.islamictree.start.repositories.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final AuthorTagRepository authorTagRepository;

    public AuthorService(AuthorRepository authorRepository, TagRepository tagRepository,
                         AuthorTagRepository authorTagRepository) {
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.authorTagRepository = authorTagRepository;
    }

    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll()
            .doOnError(error -> log.error("*** Error getting all users: {}"));
    }

    public Flux<AuthorWithTags> getAllAuthorsWithTagsBatch() {
        return authorRepository.findAll()
            .collectList()
            .flatMapMany(authors -> {
                if (authors.isEmpty()) {
                    return Flux.empty();
                }

                List<Long> authorIds = authors.stream()
                    .map(Author::getId)
                    .collect(Collectors.toList());

                return authorTagRepository.findByAuthorIds(authorIds)
                    .collectMultimap(AuthorTag::getAuthorId, AuthorTag::getTagId)
                    .flatMapMany(authorTagMap -> {
                        Set<Long> tagIds = authorTagMap.values().stream()
                            .flatMap(Collection::stream)
                            .collect(Collectors.toSet());

                        return tagRepository.findAllById(tagIds)
                            .collectMap(Tag::getId)
                            .flatMapMany(tagMap ->
                                Flux.fromIterable(authors)
                                    .map(author -> {
                                        Collection<Long> authorTagIds = authorTagMap.get(author.getId());
                                        List<Tag> authorTags = authorTagIds != null ?
                                            authorTagIds.stream()
                                                .map(tagMap::get)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.toList()) :
                                            Collections.emptyList();

                                        return new AuthorWithTags(author, authorTags);
                                    })
                            );
                    });
            })
            .doOnError(error -> log.error("*** Error getting all users: {}"));
    }

    public Mono<Author> getAuthorById(Long id) {
        return authorRepository.findById(id)
            .switchIfEmpty(Mono.defer(Mono::empty))
            .doOnError(error -> log.error("*** Error getting author by id: {}", id));
    }

    public Mono<AuthorWithTags> getAuthorWithTagsById(Long id) {
        return authorRepository.findById(id)
            .switchIfEmpty(Mono.empty())
            .flatMap(author ->
                tagRepository.findTagsByAuthorId(id)
                    .collectList()
                    .map(tags -> new AuthorWithTags(author, tags))
            )
            .doOnError(error -> log.error("*** Error getting author with tags by id: {}", id));
    }

    public Mono<Author> saveAuthor(Author author) {
        return authorRepository.save(author)
            .doOnError(error -> log.error("*** Error saving author: {}", author));
    }

    public Mono<AuthorWithTags> saveAuthorWithTags(Author author, List<Long> tagIds) {
        return authorRepository.save(author)
            .flatMap(savedAuthor -> {
                if (tagIds == null || tagIds.isEmpty()) {
                    return Mono.just(new AuthorWithTags(savedAuthor, Collections.emptyList()));
                }

                List<AuthorTag> authorTags = tagIds.stream()
                    .map(tagId -> new AuthorTag(savedAuthor.getId(), tagId))
                    .collect(Collectors.toList());

                return authorTagRepository.saveAll(authorTags)
                    .then(tagRepository.findAllById(tagIds)
                        .collectList()
                        .map(tags -> new AuthorWithTags(savedAuthor, tags))
                    );
            })
            .doOnError(error -> log.error("*** Error saving author with tags: {}", author));
    }

    public Mono<Author> updateAuthor(Author author) {
        return authorRepository.findById(author.getId())
            .switchIfEmpty(Mono.defer(Mono::empty))
            .flatMap(existing -> authorRepository.save(author))
            .doOnError(error -> log.error("*** Error updating author: {}", author));
    }

    public Mono<AuthorWithTags> updateAuthorWithTags(Author author, List<Long> tagIds) {
        return authorRepository.findById(author.getId())
            .switchIfEmpty(Mono.empty())
            .flatMap(existing -> authorRepository.save(author))
            .flatMap(updatedAuthor ->
                // Delete existing author-tag relationships
                authorTagRepository.deleteByAuthorId(updatedAuthor.getId())
                    .then(Mono.defer(() -> {
                        if (tagIds == null || tagIds.isEmpty()) {
                            return Mono.just(new AuthorWithTags(updatedAuthor, Collections.emptyList()));
                        }

                        List<AuthorTag> authorTags = tagIds.stream()
                            .map(tagId -> new AuthorTag(updatedAuthor.getId(), tagId))
                            .collect(Collectors.toList());

                        return authorTagRepository.saveAll(authorTags)
                            .then(tagRepository.findAllById(tagIds)
                                .collectList()
                                .map(tags -> new AuthorWithTags(updatedAuthor, tags))
                            );
                    }))
            )
            .doOnError(error -> log.error("*** Error updating author with tags: {}", author));
    }

    public Mono<Void> deleteAuthor(Long id) {
        return authorRepository.deleteById(id)
            .doOnError(error -> log.error("*** Error deleting author: {}", id));
    }
}
