package com.islamictree.start.controllers;

import com.islamictree.start.converters.AddressDtoToAddressConverter;
import com.islamictree.start.converters.AddressToAddressDtoConverter;
import com.islamictree.start.dto.AddressDto;
import com.islamictree.start.models.Address;
import com.islamictree.start.services.AddressService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;

    @Mock
    private AddressService addressService;

    @Mock
    AddressDtoToAddressConverter addressConverter;

    @Mock
    AddressToAddressDtoConverter dtoConverter;

    private Address testAddress;
    private AddressDto testAddressDto;

    @BeforeEach
    void setUp() {
        testAddress = new Address(1L, "123 Main ST", "Test City",
                "NY", "USA", "12345");
        testAddressDto = new AddressDto(1L, "123 Main ST", "Test City",
                "NY", "USA", "12345");
    }

    @Test
    @DisplayName("GET /addresses - Should return all addresses")
    void testGetAddresses_ShouldReturnAllAddresses() {
        // Arrange
        Address testAddress2 = new Address(2L, "42nd ST", "Manhattan",
                "NY", "USA", "11234");
        AddressDto testAddressDto2 = new AddressDto(2L, "42nd ST", "Manhattan",
                "NY", "USA", "11234");

        when(addressService.getAllAddresses()).thenReturn(
                Flux.just(testAddress, testAddress2));
        when(dtoConverter.convert(testAddress)).thenReturn(testAddressDto);
        when(dtoConverter.convert(testAddress2)).thenReturn(testAddressDto2);

        // Act
        Flux<AddressDto> result = addressController.getAddresses();

        // Assert
        StepVerifier.create(result)
                .expectNext(testAddressDto)
                .expectNext(testAddressDto2)
                .verifyComplete();

        verify(addressService, times(1)).getAllAddresses();
        verify(dtoConverter, times(2)).convert(any(Address.class));
    }

    @Test
    @DisplayName("GET /addresses - Should return empty Flux when no addresses exist")
    void testGetAddressById_ShouldReturnEmptyFlux_whenNoAddressesExist() {
        // Arrange
        Long addressId = 1L;
        when(addressService.getAddressById(addressId)).thenReturn(Mono.just(testAddress));
        when(dtoConverter.convert(testAddress)).thenReturn(testAddressDto);

        // Act
        Mono<AddressDto> result = addressController.getAddressById(addressId);

        // Assert
        StepVerifier.create(result)
                .expectNext(testAddressDto)
                .verifyComplete();

        verify(addressService, times(1)).getAddressById(addressId);
        verify(dtoConverter, times(1)).convert(testAddress);
    }

    @Test
    @DisplayName("GET /addresses/{id} - Should return address when found")
    void testGetAddressById_ShouldReturnAddress_WhenAddressExists() {
        // Arrange
        Long addressId = 1L;
        when(addressService.getAddressById(addressId)).thenReturn(Mono.just(testAddress));
        when(dtoConverter.convert(testAddress)).thenReturn(testAddressDto);

        // Act
        Mono<AddressDto> result = addressController.getAddressById(addressId);

        // Assert
        StepVerifier.create(result)
                .expectNext(testAddressDto)
                .verifyComplete();

        verify(addressService, times(1)).getAddressById(addressId);
        verify(dtoConverter, times(1)).convert(testAddress);
    }

    @Test
    @DisplayName("GET /addresses/{id} - Should return empty mono when address not found")
    void testGetAddressById_ShouldReturnEmpty_WhenAddressNotFound() {
        // Arrange
        Long addressId = 999L;
        when(addressService.getAddressById(addressId)).thenReturn(Mono.empty());

        // Act
        Mono<AddressDto> result = addressController.getAddressById(addressId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(addressService, times(1)).getAddressById(addressId);
        verify(dtoConverter, never()).convert(any(Address.class));
    }

    @Test
    @DisplayName("POST /addresses - Should create address successfully")
    void testCreateAddress_ShouldCreateAddress_WhenValidAddressProvided() {
        // Arrange
        AddressDto inputDto = new AddressDto();
        inputDto.setStreet("789 Pine St");
        inputDto.setCity("New City");

        Address inputAddress = new Address();
        inputAddress.setStreet("789 Pine St");
        inputAddress.setCity("New City");

        Address savedAddress = new Address();
        savedAddress.setId(3L);
        savedAddress.setStreet("789 Pine St");
        savedAddress.setCity("New City");

        AddressDto savedDto = new AddressDto();
        savedDto.setId(3L);
        savedDto.setStreet("789 Pine St");
        savedDto.setCity("New City");

        when(addressConverter.convert(inputDto)).thenReturn(inputAddress);
        when(addressService.saveOrUpdateAddress(inputAddress)).thenReturn(Mono.just(savedAddress));
        when(dtoConverter.convert(savedAddress)).thenReturn(savedDto);

        // Act
        Mono<AddressDto> result = addressController.createAddress(inputDto);

        // Assert
        StepVerifier.create(result)
                .expectNext(savedDto)
                .verifyComplete();

        verify(addressConverter, times(1)).convert(inputDto);
        verify(addressService, times(1)).saveOrUpdateAddress(inputAddress);
        verify(dtoConverter, times(1)).convert(savedAddress);
    }

    @Test
    @DisplayName("POST /addresses - Should handle error when creation fails")
    void testCreateAddress_ShouldHandleError_WhenCreationFails() {
        // Arrange
        AddressDto inputDto = new AddressDto();
        Address inputAddress = new Address();
        RuntimeException exception = new RuntimeException("Creation failed");

        when(addressConverter.convert(inputDto)).thenReturn(inputAddress);
        when(addressService.saveOrUpdateAddress(inputAddress)).thenReturn(Mono.error(exception));

        // Act
        Mono<AddressDto> result = addressController.createAddress(inputDto);

        // Assert
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(addressConverter, times(1)).convert(inputDto);
        verify(addressService, times(1)).saveOrUpdateAddress(inputAddress);
        verify(dtoConverter, never()).convert(any(Address.class));
    }

    @Test
    @DisplayName("POST /addresses/{id} - Should update address when address exists")
    void testUpdateAddress_ShouldUpdateAddress_WhenAddressExists() {
        // Arrange
        Long addressId = 1L;
        AddressDto updateDto = new AddressDto();
        updateDto.setStreet("Updated Street");
        updateDto.setCity("Updated City");

        when(addressService.getAddressById(addressId)).thenReturn(Mono.just(testAddress));
        when(dtoConverter.convert(testAddress)).thenReturn(testAddressDto);

        // Act
        Mono<AddressDto> result = addressController.updateAddress(addressId, updateDto);

        // Assert
        StepVerifier.create(result)
                .expectNext(testAddressDto)
                .verifyComplete();

        verify(addressService, times(1)).getAddressById(addressId);
        verify(dtoConverter, times(1)).convert(testAddress);
    }

    @Test
    @DisplayName("POST /addresses/{id} - Should handle when address not found for update")
    void testUpdateAddress_ShouldHandleNotFound_WhenAddressDoesNotExist() {
        // Arrange
        Long addressId = 999L;
        AddressDto updateDto = new AddressDto();
        updateDto.setStreet("Updated Street");

        when(addressService.getAddressById(addressId)).thenReturn(Mono.empty());

        // Act
        Mono<AddressDto> result = addressController.updateAddress(addressId, updateDto);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(addressService, times(1)).getAddressById(addressId);
        verify(dtoConverter, never()).convert(any(Address.class));
        verify(addressConverter, never()).convert(any(AddressDto.class));
    }

    @Test
    @DisplayName("POST /addresses/{id} - Should handle error during update")
    void testUpdateAddress_ShouldHandleError_WhenUpdateFails() {
        // Arrange
        Long addressId = 1L;
        AddressDto updateDto = new AddressDto();
        RuntimeException exception = new RuntimeException("Update failed");

        when(addressService.getAddressById(addressId)).thenReturn(Mono.error(exception));

        // Act
        Mono<AddressDto> result = addressController.updateAddress(addressId, updateDto);

        // Assert
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(addressService, times(1)).getAddressById(addressId);
        verify(dtoConverter, never()).convert(any(Address.class));
    }
}