package com.islamictree.start.services;

import com.islamictree.start.models.Address;
import com.islamictree.start.models.enums.AddressType;
import com.islamictree.start.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("AddressService unit tests")
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private Address testAddress;
    private Address existingAddress;

    @BeforeEach
    void setUp() {
        testAddress = new Address(1L, "123 Main St", "City 1", "NY",
                "USA", "10001", -73.9857, 40.7484,
                true, AddressType.RESIDENTIAL);

        existingAddress = new Address(2L, "123 Main St", "City 2", "NY",
                "USA", "10001", -73.9857, 40.7484,
                true, AddressType.RESIDENTIAL);
    }

    @Test
    @DisplayName("Testing getAllAddresses - Should return all addresses")
    void testGetAllAddresses_ShouldReturnAllAddresses() {
        // Arrange
        Address address1 = new Address(1L, "Street 1", "City 3", "WA",
                "Country 1", "12345", -73.0, 40.0, true,
                AddressType.RESIDENTIAL);
        Address address2 = new Address(2L, "Street 2", "City 4", "TX",
                "Country 2", "67890", -74.0, 41.0, false,
                AddressType.RESIDENTIAL);

        when(addressRepository.findAll()).thenReturn(Flux.just(address1, address2));

        // Act
        Flux<Address> result = addressService.getAllAddresses();

        // Assert
        assertThat(result).isNotNull();

        StepVerifier.create(result)
            .expectNext(address1)
            .expectNext(address2)
            .verifyComplete();

        verify(addressRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Testing getAddressById - Should return address")
    void testGetAddressById_ShouldReturnAddress_WhenAddressIsFound() {
        // Arrange
        when(addressRepository.findById(1L)).thenReturn(Mono.just(testAddress));

        // Act
        Mono<Address> result = addressService.getAddressById(1L);

        // Assert
        assertThat(result).isNotNull();

        StepVerifier.create(result)
            .expectNext(testAddress)
            .verifyComplete();
    }

    @Test
    @DisplayName("Testing getAddressById - Should throw exception when Address not found")
    void testGetAddressById_ShouldThrowException_WhenAddressNotFound() {
        // Arrange
        Long nonExistentId = 7L;
        when(addressRepository.findById(nonExistentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(addressService.getAddressById(nonExistentId))
            .expectErrorMessage("No address found with id: " + nonExistentId)
            .verify();

        verify(addressRepository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Testing saveAddress - Should save new address when no duplicate exists")
    void testSaveAddress_ShouldSaveNewAddress_WhenAddressDoesNotExist() {
        // Arrange
        Address newAddress = new Address("New Street", "New City", "CA",
                "USA", "90210", -118.0, 34.0, false, AddressType.RESIDENTIAL);
        Address savedAddress = new Address(3L, "New Street", "New City", "CA",
                "USA", "90210", -118.0, 34.0, false, AddressType.RESIDENTIAL);

        when(addressRepository.findByStreetAndCityAndStateAndCountryAndZipCode(
                "New Street", "New City", "CA", "USA", "90210"))
                .thenReturn(Flux.empty());

        when(addressRepository.save(newAddress)).thenReturn(Mono.just(savedAddress));

        // Act
        Mono<Address> result = addressService.saveAddress(newAddress);

        // Assert
        StepVerifier.create(result)
                .expectNext(savedAddress)
                .verifyComplete();

        verify(addressRepository, times(1)).findByStreetAndCityAndStateAndCountryAndZipCode(
                "New Street", "New City", "CA", "USA", "90210");
        verify(addressRepository, times(1)).save(newAddress);
    }

    @Test
    @DisplayName("Testing updateAddress - Should update existing address")
    void testUpdateAddress_ShouldUpdateAddress_WhenAddressExists() {
        // Arrange
        Long ADD_ID = 5L;
        Address updatedAddress = new Address(ADD_ID, "123 Main St", "City 3", "NY",
                "USA", "10001", -73.9857, 40.7484, false, AddressType.BUSINESS);

        Address existingAddress = new Address(ADD_ID, "123 Main St", "City 1", "NY",
                "USA", "10001", -73.9857, 40.7484, true, AddressType.RESIDENTIAL);

        when(addressRepository.findById(ADD_ID)).thenReturn(Mono.just(existingAddress));
        when(addressRepository.save(updatedAddress)).thenReturn(Mono.just(updatedAddress));

        // Act
        Mono<Address> result = addressService.updateAddress(updatedAddress);

        // Assert
        StepVerifier.create(result)
                .expectNext(updatedAddress)
                .verifyComplete();

        verify(addressRepository, times(1)).findById(ADD_ID);
        verify(addressRepository, times(1)).save(updatedAddress);
    }

    @Test
    @DisplayName("Testing updateAddress - Should throw exception when address not found")
    void testUpdateAddress_ShouldThrowException_WhenAddressNotFound() {
        // Arrange
        Long ADD_ID = 5L;
        Address updatedAddress = new Address(ADD_ID, "123 Main St", "City 3", "NY",
            "USA", "10001", -73.9857, 40.7484, false,
            AddressType.BUSINESS);

        when(addressRepository.findById(ADD_ID)).thenReturn(Mono.empty());

        // Act
        Mono<Address> result = addressService.updateAddress(updatedAddress);

        // Assert
        StepVerifier.create(result)
            .expectErrorMessage("Address not found with id: " + ADD_ID)
            .verify();

        verify(addressRepository, times(1)).findById(ADD_ID);
        verify(addressRepository, never()).save(updatedAddress);
    }

    @Test
    @DisplayName("Testing deleteAddressById - Should delete address when found")
    void testDeleteAddressById_ShouldDeleteAddress_WhenFound() {
        // Arrange
        Long addressId = 1L;
        when(addressRepository.findById(addressId)).thenReturn(Mono.just(testAddress));
        when(addressRepository.deleteById(addressId)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = addressService.deleteAddressById(addressId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(addressRepository, times(1)).findById(addressId);
        verify(addressRepository, times(1)).deleteById(addressId);
    }

    @Test
    @DisplayName("Testing deleteAddressById - Should throw exception when address not found")
    void testDeleteAddressById_ShouldThrowException_WhenAddressNotFound() {
        // Arrange
        Long nonExistentId = 99L;
        when(addressRepository.findById(nonExistentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(addressService.deleteAddressById(nonExistentId))
                .expectErrorMessage("Address not found with id: " + nonExistentId)
                .verify();

        verify(addressRepository, times(1)).findById(nonExistentId);
        verify(addressRepository, never()).deleteById(nonExistentId);
    }

    @Test
    @DisplayName("Testing deleteAddressById - Should handle deletion error")
    void testDeleteAddressById_ShouldHandleDeletionError() {
        // Arrange
        Long addressId = 1L;
        RuntimeException deletionError = new RuntimeException("Database constraint violation");

        when(addressRepository.findById(addressId)).thenReturn(Mono.just(testAddress));
        when(addressRepository.deleteById(addressId)).thenReturn(Mono.error(deletionError));

        // Act & Assert
        StepVerifier.create(addressService.deleteAddressById(addressId))
                .expectError(RuntimeException.class)
                .verify();

        verify(addressRepository, times(1)).findById(addressId);
        verify(addressRepository, times(1)).deleteById(addressId);
    }

    @Test
    @DisplayName("Testing deleteAddressById - Should handle findById error")
    void testDeleteAddressById_ShouldHandleFindByIdError() {
        // Arrange
        Long addressId = 1L;
        RuntimeException findError = new RuntimeException("Database connection error");

        when(addressRepository.findById(addressId)).thenReturn(Mono.error(findError));

        // Act & Assert
        StepVerifier.create(addressService.deleteAddressById(addressId))
                .expectError(RuntimeException.class)
                .verify();

        verify(addressRepository, times(1)).findById(addressId);
        verify(addressRepository, never()).deleteById(addressId);
    }
}