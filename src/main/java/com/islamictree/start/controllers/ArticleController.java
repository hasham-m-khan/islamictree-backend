package com.islamictree.start.controllers;

import com.islamictree.start.converters.ArticleToArticleDtoConverter;
import com.islamictree.start.dto.ArticleDto;
import com.islamictree.start.services.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleToArticleDtoConverter dtoConverter;

    public ArticleController(ArticleService articleService,
                             ArticleToArticleDtoConverter dtoConverter) {
        this.articleService = articleService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping("")
    public Flux<ArticleDto> getAllArticles() {
        log.info("Getting all articles");

        return articleService.getAllArticles()
            .parallel(4)
            .map(dtoConverter::convert)
            .sequential()
            .doOnError(error -> log.error("Error fetching all articles", error))
            .onErrorResume(error -> Flux.empty());
    }

    @GetMapping("/{id}")
    public Mono<ArticleDto> getArticle(@PathVariable Long id,
                                       @RequestParam(defaultValue = "false") boolean includeTags) {
        log.info("Getting article with id (includeTags: {}): {}", includeTags, id);

        if (includeTags) {
            return articleService.getArticleWithTagsById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Article not found with id: " + id )))
                .map(dtoConverter::convert)
                .doOnError(error -> log.error("error: {}", error.getMessage()));
        } else {
            // TODO: Handle if article was not found - throw error
            return articleService.getArticle(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Article not found with id: " + id )))
                .map(dtoConverter::convert)
                .doOnError(error -> log.error("error: {}", error.getMessage()));
        }
    }
}
