package com.islamictree.start.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    Long ID;
    String FIRST_NAME;
    String LAST_NAME;
    String DESCRIPTION;
    byte[] IMAGE_DATA;
    String CONTENT_TYPE;


    @BeforeEach
    void setUp() {
        ID = 1L;
        FIRST_NAME = "John";
        LAST_NAME = "Doe";
        DESCRIPTION = "This is a description";
        IMAGE_DATA = "This is some image".getBytes();
        CONTENT_TYPE = "image/jpeg";
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
        author.setDescription(DESCRIPTION);
        author.setImageData(IMAGE_DATA);
        author.setContentType(CONTENT_TYPE);

        // Assert
        assertNotNull(author);
        assertEquals(ID, author.getId());
        assertEquals(FIRST_NAME, author.getFirstName());
        assertEquals(LAST_NAME, author.getLastName());
        assertEquals(DESCRIPTION, author.getDescription());
        assertArrayEquals(IMAGE_DATA, author.getImageData());
        assertEquals(CONTENT_TYPE, author.getContentType());
    }

    @Test
    @DisplayName("Test All-arguments constructor for Author")
    void testAllArgumentsConstructor() {
        // Act
        Author author = new Author(ID, FIRST_NAME, LAST_NAME,
                DESCRIPTION, CONTENT_TYPE, IMAGE_DATA);

        // Assert
        assertNotNull(author);
        assertEquals(ID, author.getId());
        assertEquals(FIRST_NAME, author.getFirstName());
        assertEquals(LAST_NAME, author.getLastName());
        assertEquals(DESCRIPTION, author.getDescription());
        assertArrayEquals(IMAGE_DATA, author.getImageData());
        assertEquals(CONTENT_TYPE, author.getContentType());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Author")
    void testEqualsAndHashcode() {
        // Act
        Author author1 = new Author(ID, FIRST_NAME, LAST_NAME,
                DESCRIPTION, CONTENT_TYPE, IMAGE_DATA);
        Author author2 = new Author(ID, FIRST_NAME, LAST_NAME,
                DESCRIPTION, CONTENT_TYPE, IMAGE_DATA);
        Author author3 = new Author(225L, FIRST_NAME, LAST_NAME,
                DESCRIPTION, CONTENT_TYPE, IMAGE_DATA);

        // Assert
        assertEquals(author1, author2);
        assertEquals(author1.hashCode(), author2.hashCode());
        assertNotEquals(author1, author3);
        assertNotEquals(author1.hashCode(), author3.hashCode());
    }
}