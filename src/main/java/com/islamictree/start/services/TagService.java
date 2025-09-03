package com.islamictree.start.services;

import com.islamictree.start.converters.TagToTagDtoConverter;
import com.islamictree.start.models.Tag;
import com.islamictree.start.repositories.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagToTagDtoConverter dtoConverter;

    public TagService(TagRepository tagRepository, TagToTagDtoConverter dtoConverter) {
        this.tagRepository = tagRepository;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping("")
    public Flux<Tag> getTagsByArticleId(@RequestParam Long articleId) {
        if (articleId == null) {
            return Flux.empty();
        }

        return tagRepository.findTagsByArticleId(articleId);
    }
}
