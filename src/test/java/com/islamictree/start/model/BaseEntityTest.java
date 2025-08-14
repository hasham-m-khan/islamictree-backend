package com.islamictree.start.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseEntityTest {

    Long ID;

    @BeforeEach
    void setUp() {
        ID = 1L;
    }

    @Test
    @DisplayName("Test default constructor and setters for BaseEntity")
    void testDefaultConstructorAndSetters() {
        // Arrange
        BaseEntity baseEntity = new BaseEntity();

        // Act
        baseEntity.setId(ID);

        // Assert
        assertNotNull(baseEntity);
        assertEquals(ID, baseEntity.getId());
    }

    @Test
    @DisplayName("Test All-arguments constructor for BaseEntity")
    void testAllArgumentsConstructor() {
        // Act
        BaseEntity baseEntity = new BaseEntity(ID);

        // Assert
        assertNotNull(baseEntity);
        assertEquals(ID, baseEntity.getId());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for BaseEntity")
    void testEqualsAndHashcode() {
        // Act
        BaseEntity baseEntity1 = new BaseEntity(ID);
        BaseEntity baseEntity2 = new BaseEntity(ID);
        BaseEntity baseEntity3 = new BaseEntity(4L);

        // Assert
        assertEquals(baseEntity1, baseEntity2);
        assertEquals(baseEntity1.hashCode(), baseEntity2.hashCode());
        assertNotEquals(baseEntity1, baseEntity3);
        assertNotEquals(baseEntity1.hashCode(), baseEntity3.hashCode());
    }
}