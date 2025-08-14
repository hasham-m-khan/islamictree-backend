package com.islamictree.start.model;

import com.islamictree.start.model.enums.AddressType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    Long ID;
    String STREET;
    String CITY;
    String STATE;
    String COUNTRY;
    String ZIP_CODE;
    Double LONGITUDE;
    Double LATITUDE;
    Boolean IS_VALIDATED;
    Enum<AddressType> ADDRESS_TYPE;

    @BeforeEach
    void setUp() {
        ID = 1L;
        STREET = "123 Main ST";
        CITY = "San Francisco";
        STATE = "CA";
        COUNTRY = "USA";
        ZIP_CODE = "12345";
        LONGITUDE = -122.4321;
        LATITUDE = 41.4321;
        IS_VALIDATED = false;
        ADDRESS_TYPE = Enum.valueOf(AddressType.class, "RESIDENTIAL");
    }

    @Test
    @DisplayName("Test default constructor and setters for Address")
    void testDefaultConstructorAndSetters() {
        // Arrange
        Address address = new Address();

        // Act
        address.setId(ID);
        address.setStreet(STREET);
        address.setCity(CITY);
        address.setState(STATE);
        address.setCountry(COUNTRY);
        address.setZipCode(ZIP_CODE);
        address.setLongitude(LONGITUDE);
        address.setLatitude(LATITUDE);
        address.setIsValidated(IS_VALIDATED);
        address.setAddressType(ADDRESS_TYPE);

        // Assert
        assertNotNull(address);
        assertEquals(ID, address.getId());
        assertEquals(STREET, address.getStreet());
        assertEquals(CITY, address.getCity());
        assertEquals(STATE, address.getState());
        assertEquals(COUNTRY, address.getCountry());
        assertEquals(ZIP_CODE, address.getZipCode());
        assertEquals(LONGITUDE, address.getLongitude());
        assertEquals(LATITUDE, address.getLatitude());
        assertEquals(IS_VALIDATED, address.getIsValidated());
        assertEquals(ADDRESS_TYPE, address.getAddressType());
    }

    @Test
    @DisplayName("Test All-arguments constructor for Address")
    void testAllArgumentsConstructor() {
        // Arrange
        Address address = new Address(ID, STREET, CITY, STATE, COUNTRY, ZIP_CODE,
                LONGITUDE, LATITUDE, IS_VALIDATED, ADDRESS_TYPE);

        // Assert
        assertNotNull(address);
        assertEquals(ID, address.getId());
        assertEquals(STREET, address.getStreet());
        assertEquals(CITY, address.getCity());
        assertEquals(STATE, address.getState());
        assertEquals(COUNTRY, address.getCountry());
        assertEquals(ZIP_CODE, address.getZipCode());
        assertEquals(LONGITUDE, address.getLongitude());
        assertEquals(LATITUDE, address.getLatitude());
        assertEquals(IS_VALIDATED, address.getIsValidated());
        assertEquals(ADDRESS_TYPE, address.getAddressType());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Address")
    void testEqualsAndHashcode() {
        // Arrange
        Address address1 = new Address(ID, STREET, CITY, STATE, COUNTRY, ZIP_CODE,
                LONGITUDE, LATITUDE, IS_VALIDATED, ADDRESS_TYPE);
        Address address2 = new Address(ID, STREET, CITY, STATE, COUNTRY, ZIP_CODE,
                LONGITUDE, LATITUDE, IS_VALIDATED, ADDRESS_TYPE);
        Address address3 = new Address(5L, STREET, CITY, STATE, COUNTRY, ZIP_CODE,
                LONGITUDE, LATITUDE, IS_VALIDATED, ADDRESS_TYPE);

        // Assert
        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());
        assertNotEquals(address1, address3);
        assertNotEquals(address1.hashCode(), address3.hashCode());
    }


}