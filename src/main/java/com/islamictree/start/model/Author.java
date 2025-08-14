package com.islamictree.start.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Author author = (Author) o;
        return Objects.equals(description, author.description) && Arrays.equals(imageData, author.imageData) && Objects.equals(contentType, author.contentType);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Arrays.hashCode(imageData);
        result = 31 * result + Objects.hashCode(contentType);
        return result;
    }
}
