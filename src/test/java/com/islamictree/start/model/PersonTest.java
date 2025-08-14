package com.islamictree.start.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    Long ID;
    String FIRST_NAME;
    String LAST_NAME;

    @BeforeEach
    void setUp() {
        ID = 1L;
        FIRST_NAME = "John";
        LAST_NAME = "Doe";
    }

    @Test
    @DisplayName("Test default constructor and setters for Person")
    void testDefaultConstructorAndSetters() {
        // Arrange
        Person person = new Person();

        // Act
        person.setId(ID);
        person.setFirstName(FIRST_NAME);
        person.setLastName(LAST_NAME);

        // Assert
        assertNotNull(person);
        assertEquals(ID, person.getId());
        assertEquals(FIRST_NAME, person.getFirstName());
        assertEquals(LAST_NAME, person.getLastName());
    }

    @Test
    @DisplayName("Test All-arguments constructor for Person")
    void testAllArgumentsConstructor() {
        // Act
        Person person = new Person(ID, FIRST_NAME, LAST_NAME);

        // Assert
        assertNotNull(person);
        assertEquals(ID, person.getId());
        assertEquals(FIRST_NAME, person.getFirstName());
        assertEquals(LAST_NAME, person.getLastName());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Person")
    void testEqualsAndHashcode() {
        // Act
        Person person1 = new Person(ID, FIRST_NAME, LAST_NAME);
        Person person2 = new Person(ID, FIRST_NAME, LAST_NAME);
        Person person3 = new Person(798L, FIRST_NAME, LAST_NAME);

        // Assert
        assertEquals(person1, person2);
        assertEquals(person1.hashCode(), person2.hashCode());
        assertNotEquals(person1, person3);
        assertNotEquals(person1.hashCode(), person3.hashCode());
    }
}