package com.islamictree.start.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    Long ID;
    String STREET_NAME;
    String CITY;
    String STATE;
    String ZIPCODE;
    Double LATITUDE;
    Double LONGITUDE;

    @BeforeEach
    void setUp() {
        ID = 1L;
        STREET_NAME = "123 Main ST";
        CITY = "San Francisco";
        STATE = "CA";
        ZIPCODE = "12345";
        LATITUDE = 41.4321;
        LONGITUDE = -122.4321;
    }

    @Test
    @DisplayName("Test default constructor and setters for Address")
    void testDefaultConstructorAndSetters() {
        // Arrange
        Address address = new Address();

        // Act
        address.setId(ID);
        address.setStreetName(STREET_NAME);
        address.setCity(CITY);
        address.setState(STATE);
        address.setZipCode(ZIPCODE);
        address.setLongitude(LONGITUDE);
        address.setLatitude(LATITUDE);

        // Assert
        assertNotNull(address);
        assertEquals(ID, address.getId());
        assertEquals(STREET_NAME, address.getStreetName());
        assertEquals(CITY, address.getCity());
        assertEquals(STATE, address.getState());
        assertEquals(ZIPCODE, address.getZipCode());
        assertEquals(LONGITUDE, address.getLongitude(), 0);
        assertEquals(LATITUDE, address.getLatitude(), 0);
    }

    @Test
    @DisplayName("Test All-arguments constructor for Address")
    void testAllArgumentsConstructor() {
        // Arrange
        Address address = new Address(ID, STREET_NAME, CITY, STATE,
                ZIPCODE, LONGITUDE, LATITUDE);

        // Assert
        assertNotNull(address);
        assertEquals(ID, address.getId());
        assertEquals(STREET_NAME, address.getStreetName());
        assertEquals(CITY, address.getCity());
        assertEquals(STATE, address.getState());
        assertEquals(ZIPCODE, address.getZipCode());
        assertEquals(LONGITUDE, address.getLongitude());
        assertEquals(LATITUDE, address.getLatitude());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Address")
    void testEqualsAndHashcode() {
        // Arrange
        Address address1 = new Address(ID, STREET_NAME, CITY, STATE,
                ZIPCODE, LONGITUDE, LATITUDE);
        Address address2 = new Address(ID, STREET_NAME, CITY, STATE,
                ZIPCODE, LONGITUDE, LATITUDE);
        Address address3 = new Address(3L, STREET_NAME, CITY, STATE,
                ZIPCODE, LONGITUDE, LATITUDE);

        // Assert
        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());
        assertNotEquals(address1, address3);
        assertNotEquals(address1.hashCode(), address3.hashCode());
    }


}