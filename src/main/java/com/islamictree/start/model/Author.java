package com.islamictree.start.model;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@Table(name = "authors")
public class Author extends Person {

    @Column("birth_date")
    private LocalDate birthDate;

    @Column("death_date")
    private LocalDate deathDate;

    @Column("birth_place")
    private String birthPlace;

    @Column("resting_place")
    private String restingPlace;

    @Column("description")
    private String description;

    @Column("image_data")
    private byte[] imageData;

    @Column("mime_type")
    private String contentType;

    @Column("created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Timestamp updatedAt;

    public Author(Long id, String firstName, String lastName, LocalDate birthDate,
                  LocalDate deathDate, String birthPlace, String restingPlace,
                  String description, byte[] imageData, String contentType) {
        super(id, firstName, lastName);
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.birthPlace = birthPlace;
        this.restingPlace = restingPlace;
        this.description = description;
        this.imageData = imageData;
        this.contentType = contentType;
    }

    public Author(String firstName, String lastName, LocalDate birthDate,
                  LocalDate deathDate, String birthPlace, String restingPlace,
                  String description, byte[] imageData, String contentType) {
        super(firstName, lastName);
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.birthPlace = birthPlace;
        this.restingPlace = restingPlace;
        this.description = description;
        this.imageData = imageData;
        this.contentType = contentType;
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
