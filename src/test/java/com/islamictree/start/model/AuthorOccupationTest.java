package com.islamictree.start.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorOccupationTest {

    private Long AUTHOR_ID;
    private Long OCCUPATION_ID;

    @BeforeEach
    void setUp() {
        AUTHOR_ID = 1L;
        OCCUPATION_ID = 2L;
    }

    @Test
    @DisplayName("Test default constructor and setters for AuthorOccupation")
    void testDefaultConstructorAndSetters() {
        // Arrange
        AuthorOccupation authorOccupation = new AuthorOccupation();

        // Act
        authorOccupation.setAuthorId(AUTHOR_ID);
        authorOccupation.setOccupationId(OCCUPATION_ID);

        // Assert
        assertNotNull(authorOccupation);
        assertEquals(AUTHOR_ID, authorOccupation.getAuthorId());
        assertEquals(OCCUPATION_ID, authorOccupation.getOccupationId());
    }

    @Test
    @DisplayName("Test All-arguments constructor for AuthorOccupation")
    void testAllArgumentsConstructor() {
        // Act
        AuthorOccupation authorOccupation = new AuthorOccupation(AUTHOR_ID, OCCUPATION_ID);

        // Assert
        assertNotNull(authorOccupation);
        assertEquals(AUTHOR_ID, authorOccupation.getAuthorId());
        assertEquals(OCCUPATION_ID, authorOccupation.getOccupationId());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for AuthorOccupation")
    void testEqualsAndHashcode() {
        // Act
        AuthorOccupation authorOccupation1 = new AuthorOccupation(AUTHOR_ID, OCCUPATION_ID);
        AuthorOccupation authorOccupation2 = new AuthorOccupation(AUTHOR_ID, OCCUPATION_ID);
        AuthorOccupation authorOccupation3 = new AuthorOccupation(123L, OCCUPATION_ID);

        // Assert
        assertEquals(authorOccupation1, authorOccupation2);
        assertEquals(authorOccupation1.hashCode(), authorOccupation2.hashCode());
        assertNotEquals(authorOccupation1, authorOccupation3);
        assertNotEquals(authorOccupation1.hashCode(), authorOccupation3.hashCode());
    }
}