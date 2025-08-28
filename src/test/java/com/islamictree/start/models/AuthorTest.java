package com.islamictree.start.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    Long ID;
    String FIRST_NAME;
    String LAST_NAME;
    LocalDate BIRTH_DATE;
    LocalDate DEATH_DATE;
    String BIRTH_PLACE;
    String DEATH_PLACE;
    String DESCRIPTION;
    byte[] IMAGE_DATA;
    String MIME_TYPE;


    @BeforeEach
    void setUp() {
        ID = 1L;
        FIRST_NAME = "John";
        LAST_NAME = "Doe";
        BIRTH_DATE = LocalDate.of(1990, 1, 1);
        DEATH_DATE = LocalDate.of(2000, 1, 1);
        BIRTH_PLACE = "Mumbai";
        DEATH_PLACE = "Bangalore";
        DESCRIPTION = "This is a description";
        IMAGE_DATA = "This is some image".getBytes();
        MIME_TYPE = "image/jpeg";
    }

    @Test
    @DisplayName("Test default constructor and setters for Author")
    void testDefaultConstructorAndSetters() {
        // Arrange
        Author author = new Author();

        // Act
        author.setId(ID);
        author.setFirstName(FIRST_NAME);
        author.setLastName(LAST_NAME);
        author.setBirthDate(BIRTH_DATE);
        author.setDeathDate(DEATH_DATE);
        author.setBirthPlace(BIRTH_PLACE);
        author.setRestingPlace(DEATH_PLACE);
        author.setDescription(DESCRIPTION);
        author.setImageData(IMAGE_DATA);
        author.setMimeType(MIME_TYPE);

        // Assert
        assertNotNull(author);
        assertEquals(ID, author.getId());
        assertEquals(FIRST_NAME, author.getFirstName());
        assertEquals(LAST_NAME, author.getLastName());
        assertEquals(BIRTH_DATE, author.getBirthDate());
        assertEquals(DEATH_DATE, author.getDeathDate());
        assertEquals(BIRTH_PLACE, author.getBirthPlace());
        assertEquals(DEATH_PLACE, author.getRestingPlace());
        assertEquals(DESCRIPTION, author.getDescription());
        assertArrayEquals(IMAGE_DATA, author.getImageData());
        assertEquals(MIME_TYPE, author.getMimeType());
    }

    @Test
    @DisplayName("Test All-arguments constructor for Author")
    void testAllArgumentsConstructor() {
        // Act
        Author author = new Author(ID, FIRST_NAME, LAST_NAME, BIRTH_DATE,
                DEATH_DATE, BIRTH_PLACE, DEATH_PLACE, DESCRIPTION, IMAGE_DATA,
                MIME_TYPE);

        // Assert
        assertNotNull(author);
        assertEquals(ID, author.getId());
        assertEquals(FIRST_NAME, author.getFirstName());
        assertEquals(LAST_NAME, author.getLastName());
        assertEquals(BIRTH_DATE, author.getBirthDate());
        assertEquals(DEATH_DATE, author.getDeathDate());
        assertEquals(BIRTH_PLACE, author.getBirthPlace());
        assertEquals(DEATH_PLACE, author.getRestingPlace());
        assertEquals(DESCRIPTION, author.getDescription());
        assertArrayEquals(IMAGE_DATA, author.getImageData());
        assertEquals(MIME_TYPE, author.getMimeType());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Author")
    void testEqualsAndHashcode() {
        // Act
        Author author1 = new Author(ID, FIRST_NAME, LAST_NAME, BIRTH_DATE,
                DEATH_DATE, BIRTH_PLACE, DEATH_PLACE, DESCRIPTION, IMAGE_DATA,
                MIME_TYPE);
        Author author2 = new Author(ID, FIRST_NAME, LAST_NAME, BIRTH_DATE,
                DEATH_DATE, BIRTH_PLACE, DEATH_PLACE, DESCRIPTION, IMAGE_DATA,
                MIME_TYPE);
        Author author3 = new Author(213L, FIRST_NAME, LAST_NAME, BIRTH_DATE,
                DEATH_DATE, BIRTH_PLACE, DEATH_PLACE, DESCRIPTION, IMAGE_DATA,
                MIME_TYPE);

        // Assert
        assertEquals(author1, author2);
        assertEquals(author1.hashCode(), author2.hashCode());
        assertNotEquals(author1, author3);
        assertNotEquals(author1.hashCode(), author3.hashCode());
    }
}