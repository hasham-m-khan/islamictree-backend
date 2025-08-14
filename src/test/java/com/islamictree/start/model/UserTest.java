package com.islamictree.start.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    Long ID;
    String FIRST_NAME;
    String LAST_NAME;
    String TELEPHONE;
    Long ADDRESS_ID;

    @BeforeEach
    void setUp() {
        ID = 1L;
        FIRST_NAME = "John";
        LAST_NAME = "Doe";
        TELEPHONE = "123456789";
        ADDRESS_ID = 4L;
    }

    @Test
    @DisplayName("Test default constructor and setters for User")
    void testDefaultConstructorAndSetters() {
        // Arrange
        User user = new User();

        // Act
        user.setId(ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setPhone(TELEPHONE);
        user.setAddressId(ADDRESS_ID);

        // Assert
        assertNotNull(user);
        assertEquals(ID, user.getId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(TELEPHONE, user.getPhone());
        assertEquals(ADDRESS_ID, user.getAddressId());
    }

    @Test
    @DisplayName("Test All-arguments constructor for Tag")
    void testAllArgumentsConstructor() {
        // Act
        User user = new User(ID, FIRST_NAME, LAST_NAME, TELEPHONE,
                ADDRESS_ID);

        // Assert
        assertNotNull(user);
        assertEquals(ID, user.getId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(TELEPHONE, user.getPhone());
        assertEquals(ADDRESS_ID, user.getAddressId());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Tag")
    void testEqualsAndHashcode() {
        // Act
        User user1 = new User(ID, FIRST_NAME, LAST_NAME, TELEPHONE,
                ADDRESS_ID);
        User user2 = new User(ID, FIRST_NAME, LAST_NAME, TELEPHONE,
                ADDRESS_ID);
        User user3 = new User(345L, FIRST_NAME, LAST_NAME, TELEPHONE,
                ADDRESS_ID);

        // Assert
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1, user3);
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
}