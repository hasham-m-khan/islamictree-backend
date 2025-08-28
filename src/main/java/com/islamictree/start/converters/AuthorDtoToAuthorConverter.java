package com.islamictree.start.converters;

import com.islamictree.start.dto.AuthorDto;
import com.islamictree.start.models.Author;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AuthorDtoToAuthorConverter implements Converter<AuthorDto, Author> {

    @Override
    public Author convert(AuthorDto source) {
        if (source == null) {
            return null;
        }

        return new Author(
            source.getId(),
            source.getFirstName(),
            source.getLastName(),
            source.getBirthDate(),
            source.getDeathDate(),
            source.getBirthPlace(),
            source.getDeathPlace(),
            source.getDescription(),
            source.getImageData(),
            source.getMimeType()
        );
    }
}
