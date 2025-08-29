package com.islamictree.start.models.wrappers;

import com.islamictree.start.models.Author;
import com.islamictree.start.models.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorWithTags {
    private Author author;
    private List<Tag> tags;
}
