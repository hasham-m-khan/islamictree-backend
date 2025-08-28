package com.islamictree.start.converters;

import com.islamictree.start.dto.AuthorDto;
import com.islamictree.start.models.Author;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AuthorToAuthorDtoConverter implements Converter<Author, AuthorDto> {

    @Override
    public AuthorDto convert(Author source) {
        if (source == null) {
            return null;
        }

        return new AuthorDto(
            source.getId(),
            source.getFirstName(),
            source.getLastName(),
            source.getBirthDate(),
            source.getDeathDate(),
            source.getBirthPlace(),
            source.getRestingPlace(),
            source.getDescription(),
            source.getImageData(),
            source.getMimeType(),
            source.getCreatedAt(),
            source.getUpdatedAt()
        );
    }
}
