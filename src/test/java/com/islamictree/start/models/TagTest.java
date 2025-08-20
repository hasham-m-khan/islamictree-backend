package com.islamictree.start.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    Long ID;
    String NAME;

    @BeforeEach
    void setUp() {
        ID = 1L;
        NAME = "test";
    }

    @Test
    @DisplayName("Test default constructor and setters for Tag")
    void testDefaultConstructorAndSetters() {
        // Arrange
        Tag tag = new Tag();

        // Act
        tag.setId(ID);
        tag.setName(NAME);

        // Assert
        assertNotNull(tag);
        assertEquals(ID, tag.getId());
        assertEquals(NAME, tag.getName());
    }

    @Test
    @DisplayName("Test All-arguments constructor for Tag")
    void testAllArgumentsConstructor() {
        // Act
        Tag tag = new Tag(ID, NAME);

        // Assert
        assertNotNull(tag);
        assertEquals(ID, tag.getId());
        assertEquals(NAME, tag.getName());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Tag")
    void testEqualsAndHashcode() {
        // Act
        Tag tag1 = new Tag(ID, NAME);
        Tag tag2 = new Tag(ID, NAME);
        Tag tag3 = new Tag(123L, NAME);

        // Assert
        assertEquals(tag1, tag2);
        assertEquals(tag1.hashCode(), tag2.hashCode());
        assertNotEquals(tag1, tag3);
        assertNotEquals(tag1.hashCode(), tag3.hashCode());
    }
}