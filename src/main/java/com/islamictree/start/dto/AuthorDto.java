package com.islamictree.start.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String birthPlace;
    private String deathPlace;
    private String description;
    private byte[] imageData;
    private String mimeType;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public AuthorDto(String firstName, String lastName, LocalDate birthDate, LocalDate deathDate,
                     String birthPlace, String deathPlace, String description, byte[] imageData,
                     String mimeType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.birthPlace = birthPlace;
        this.deathPlace = deathPlace;
        this.description = description;
        this.imageData = imageData;
        this.mimeType = mimeType;
    }
}
