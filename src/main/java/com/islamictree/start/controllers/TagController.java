package com.islamictree.start.controllers;

import com.islamictree.start.converters.TagToTagDtoConverter;
import com.islamictree.start.dto.TagDto;
import com.islamictree.start.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;
    private final TagToTagDtoConverter dtoConvrter;

    public TagController(TagService tagService, TagToTagDtoConverter dtoConvrter) {
        this.tagService = tagService;
        this.dtoConvrter = dtoConvrter;
    }

    @GetMapping({"", "/"})
    public Flux<TagDto> getTagsByArticleId(@RequestParam Long articleId) {

        return tagService.getTagsByArticleId(articleId)
                .map(dtoConvrter::convert);
    }
}
