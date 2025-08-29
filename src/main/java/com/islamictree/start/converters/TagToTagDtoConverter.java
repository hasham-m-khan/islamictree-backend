package com.islamictree.start.converters;

import com.islamictree.start.dto.TagDto;
import com.islamictree.start.models.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagToTagDtoConverter implements Converter<Tag, TagDto> {

    @Override
    public TagDto convert(Tag source) {
        if (source == null) {
            return null;
        }

        return new TagDto(source.getId(), source.getName());
    }
}
