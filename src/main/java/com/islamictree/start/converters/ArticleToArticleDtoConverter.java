package com.islamictree.start.converters;

import com.islamictree.start.dto.ArticleDto;
import com.islamictree.start.models.Article;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class ArticleToArticleDtoConverter implements Converter<Article, ArticleDto> {

    private final TagToTagDtoConverter tagDtoConverter = new TagToTagDtoConverter();
    private final AuthorToAuthorDtoConverter authorDtoConverter = new AuthorToAuthorDtoConverter();

    @Override
    public ArticleDto convert(Article source) {
        if (source == null) {
            return null;
        }

        return new ArticleDto(
            source.getId(),
            source.getTitle(),
            source.getBlurb(),
            source.getContent(),
            source.getSource(),
            source.getDatePublished(),
            source.getImageUrl(),
            source.getCreatedAt(),
            source.getUpdatedAt(),
            source.getAuthors().stream()
                .map(authorDtoConverter::convert)
                .collect(Collectors.toList()),
            source.getTags().stream()
                .map(tagDtoConverter::convert)
                .collect(Collectors.toList())
        );
    }
}
