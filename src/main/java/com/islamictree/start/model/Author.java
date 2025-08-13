package com.islamictree.start.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@Table(name = "authors")
public class Author extends Person {

    @Column("description")
    private String description;

    @Column("image_data")
    private byte[] imageData;

    @Column("content_type")
    private String contentType;

    public Author(String firstName, String lastName, String description,
                  String contentType, byte[] imageData) {
        super(firstName, lastName);
        this.description = description;
        this.contentType = contentType;
        this.imageData = imageData;
    }

    public Author(Long id, String firstName, String lastName, String description,
                  String contentType, byte[] imageData) {
        super(id, firstName, lastName);
        this.description = description;
        this.contentType = contentType;
        this.imageData = imageData;
    }
}
