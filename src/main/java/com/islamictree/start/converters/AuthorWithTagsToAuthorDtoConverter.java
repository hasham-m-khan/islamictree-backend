package com.islamictree.start.converters;

import com.islamictree.start.dto.AuthorDto;
import com.islamictree.start.dto.TagDto;
import com.islamictree.start.models.Author;
import com.islamictree.start.models.wrappers.AuthorWithTags;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorWithTagsToAuthorDtoConverter implements Converter<AuthorWithTags, AuthorDto> {

    private final TagToTagDtoConverter tagConverter;

    public AuthorWithTagsToAuthorDtoConverter(TagToTagDtoConverter tagConverter) {
        this.tagConverter = tagConverter;
    }

    @Override
    public AuthorDto convert(AuthorWithTags source) {
        if (source == null) {
            return null;
        }

        Author author = source.getAuthor();
        List<TagDto> tagDtos = source.getTags().stream()
            .map(tagConverter::convert)
            .toList();

        return new AuthorDto(
            author.getId(),
            author.getFirstName(),
            author.getLastName(),
            author.getBirthDate(),
            author.getDeathDate(),
            author.getBirthPlace(),
            author.getRestingPlace(),
            author.getDescription(),
            author.getImageData(),
            author.getMimeType(),
            author.getCreatedAt(),
            author.getUpdatedAt(),
            tagDtos
        );
    }
}
