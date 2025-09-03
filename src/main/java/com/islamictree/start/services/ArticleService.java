package com.islamictree.start.services;

import com.islamictree.start.models.*;
import com.islamictree.start.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {

    private final ArticleTagRepository articleTagRepository;
    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final AuthorArticleRepository authorArticleRepository;

    public ArticleService(ArticleTagRepository articleTagRepository,
                          TagRepository tagRepository, ArticleRepository articleRepository,
                          AuthorRepository authorRepository,
                          AuthorArticleRepository authorArticleRepository) {
        this.articleTagRepository = articleTagRepository;
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.authorArticleRepository = authorArticleRepository;
    }

    @Transactional(readOnly = true)
    public Flux<Article> getAllArticles() {
        return articleRepository.findAll()
            .collectList()
            .flatMapMany(this::populateArticlesRelationshipsBulk);
    }

    public Mono<Article> getArticle(Long id) {
        return articleRepository.findById(id)
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Article> getArticleWithTagsById(Long id) {
        return articleRepository.findById(id)
            .switchIfEmpty(Mono.empty())
            .flatMap(article ->
               authorRepository.findAuthorsByArticleId(id)
                   .collectList()
                   .map(authors -> {
                       article.setAuthors(authors);
                       return article;
                   })
                   .flatMap(articleWithAuthors ->
                       tagRepository.findTagsByArticleId(id)
                           .collectList()
                           .map(tags -> {
                               articleWithAuthors.setTags(tags);
                               return articleWithAuthors;
                           })
                   )
            );
    }

    private Flux<Article> populateArticlesRelationshipsBulk(List<Article> articles) {
        if (articles.isEmpty()) {
            return Flux.fromIterable(articles);
        }

        List<Long> articleIds = articles.stream()
                .map(Article::getId)
                .collect(Collectors.toList());

        Mono<Map<Long, List<Author>>> authorsMapMono = getAuthorsMapByArticleIds(articleIds);
        Mono<Map<Long, List<Tag>>> tagsMapMono = getTagsMapByArticleIds(articleIds);

        return Mono.zip(authorsMapMono, tagsMapMono)
            .flatMapMany(tuple -> {
                Map<Long, List<Author>> authorsMap = tuple.getT1();
                Map<Long, List<Tag>> tagsMap = tuple.getT2();

                return Flux.fromIterable(articles)
                    .map(article -> {
                        article.setAuthors(authorsMap.getOrDefault(article.getId(), new ArrayList<>()));
                        article.setTags(tagsMap.getOrDefault(article.getId(), new ArrayList<>()));
                        return article;
                    });
            });
    }

    private Mono<Map<Long, List<Author>>> getAuthorsMapByArticleIds(List<Long> articleIds) {
        return authorArticleRepository.findByArticleIdIn(articleIds)
            .collectMultimap(AuthorArticle::getArticleId, AuthorArticle::getAuthorId)
            .flatMap(multiMap -> {
                Set<Long> allAuthorIds = multiMap.values().stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());

                return authorRepository.findAllById(allAuthorIds)
                    .collectMap(Author::getId)
                    .map(authorMap -> {
                        Map<Long, List<Author>> result = new HashMap<>();
                        multiMap.forEach((articleId, authorIds) -> {
                            List<Author> authors = authorIds.stream()
                                .map(authorMap::get)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
                            result.put(articleId, authors);
                        });
                        return result;
                    });
            });
    }

    private Mono<Map<Long, List<Tag>>> getTagsMapByArticleIds(List<Long> articleIds) {
        return articleTagRepository.findByArticleIdIn(articleIds)
            .collectMultimap(ArticleTag::getArticleId, ArticleTag::getTagId)
            .flatMap(multiMap -> {
                Set<Long> allTagIds = multiMap.values().stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());

                return tagRepository.findAllById(allTagIds)
                    .collectMap(Tag::getId)
                    .map(tagMap -> {
                        Map<Long, List<Tag>> result = new HashMap<>();
                        multiMap.forEach((articleId, tagIds) -> {
                            List<Tag> tags = tagIds.stream()
                                .map(tagMap::get)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
                            result.put(articleId, tags);
                        });
                        return result;
                    });
            });
    }
}
