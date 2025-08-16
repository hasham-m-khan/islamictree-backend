package com.islamictree.start.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    Long ID;
    String FIRST_NAME;
    String LAST_NAME;
    String EMAIL;
    String PASSWORD_HASH;
    String TELEPHONE;
    Long ADDRESS_ID;
    Boolean IS_ACTIVE;
    Boolean EMAIL_VERIFIED;

    @BeforeEach
    void setUp() {
        ID = 1L;
        FIRST_NAME = "John";
        LAST_NAME = "Doe";
        EMAIL = "test@xyz.com";
        PASSWORD_HASH = "123456";
        TELEPHONE = "123456789";
        ADDRESS_ID = 4L;
        IS_ACTIVE = true;
        EMAIL_VERIFIED = false;
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
        user.setEmail(EMAIL);
        user.setPasswordHash(PASSWORD_HASH);
        user.setPhone(TELEPHONE);
        user.setAddressId(ADDRESS_ID);
        user.setIsActive(IS_ACTIVE);
        user.setEmailVerified(EMAIL_VERIFIED);

        // Assert
        assertNotNull(user);
        assertEquals(ID, user.getId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD_HASH, user.getPasswordHash());
        assertEquals(TELEPHONE, user.getPhone());
        assertEquals(ADDRESS_ID, user.getAddressId());
        assertEquals(IS_ACTIVE, user.getIsActive());
        assertEquals(EMAIL_VERIFIED, user.getEmailVerified());
    }

    @Test
    @DisplayName("Test All-arguments constructor for Tag")
    void testAllArgumentsConstructor() {
        // Act
        User user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD_HASH,
                TELEPHONE, ADDRESS_ID, IS_ACTIVE, EMAIL_VERIFIED);

        // Assert
        assertNotNull(user);
        assertEquals(ID, user.getId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD_HASH, user.getPasswordHash());
        assertEquals(TELEPHONE, user.getPhone());
        assertEquals(ADDRESS_ID, user.getAddressId());
        assertEquals(IS_ACTIVE, user.getIsActive());
        assertEquals(EMAIL_VERIFIED, user.getEmailVerified());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Tag")
    void testEqualsAndHashcode() {
        // Act
        User user1 = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD_HASH,
                TELEPHONE, ADDRESS_ID, IS_ACTIVE, EMAIL_VERIFIED);
        User user2 = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD_HASH,
                TELEPHONE, ADDRESS_ID, IS_ACTIVE, EMAIL_VERIFIED);
        User user3 = new User(34L, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD_HASH,
                TELEPHONE, ADDRESS_ID, IS_ACTIVE, EMAIL_VERIFIED);

        // Assert
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1, user3);
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
}