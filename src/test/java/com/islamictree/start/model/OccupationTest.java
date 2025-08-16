package com.islamictree.start.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OccupationTest {

    Long ID;
    String NAME;

    @BeforeEach
    void setUp() {
        ID = 1L;
        NAME = "test";
    }

    @Test
    @DisplayName("Test default constructor and setters for Occupation")
    void testDefaultConstructorAndSetters() {
        // Arrange
        Occupation occupation = new Occupation();

        // Act
        occupation.setId(ID);
        occupation.setName(NAME);

        // Assert
        assertNotNull(occupation);
        assertEquals(ID, occupation.getId());
        assertEquals(NAME, occupation.getName());
    }

    @Test
    @DisplayName("Test All-arguments constructor for Occupation")
    void testAllArgumentsConstructor() {
        // Act
        Occupation occupation = new Occupation(ID, NAME);

        // Assert
        assertNotNull(occupation);
        assertEquals(ID, occupation.getId());
        assertEquals(NAME, occupation.getName());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Occupation")
    void testEqualsAndHashcode() {
        // Act
        Occupation occupation1 = new Occupation(ID, NAME);
        Occupation occupation2 = new Occupation(ID, NAME);
        Occupation occupation3 = new Occupation(23L, NAME);

        // Assert
        assertEquals(occupation1, occupation2);
        assertEquals(occupation1.hashCode(), occupation2.hashCode());
        assertNotEquals(occupation1, occupation3);
        assertNotEquals(occupation1.hashCode(), occupation3.hashCode());
    }
}