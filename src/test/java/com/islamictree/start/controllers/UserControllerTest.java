package com.islamictree.start.controllers;

import com.islamictree.start.converters.UserDtoToUserConverter;
import com.islamictree.start.converters.UserToUserDtoConverter;
import com.islamictree.start.dto.UserDto;
import com.islamictree.start.models.User;
import com.islamictree.start.services.UserService;
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
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    UserToUserDtoConverter dtoConverter;

    @Mock
    UserDtoToUserConverter userConverter;

    Long TEST_ID = 1L;
    User testUser;
    UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = new User(TEST_ID, "John", "Doe", "jdoe@xyzmail.com",
            "some hash", "123-234-2349", true, true);

        testUserDto = new UserDto(TEST_ID, "John", "Doe", "jdoe@xyzmail.com",
            "some hash", "123-234-2349", true, true);
    }
    
    @Test
    @DisplayName("GET /users - Should return all users")
    void testGetAllUsers_ShouldReturnAllUsers() {
        // Arrange
        User testUser2 = new User(3L, "John", "Doe", "jdoe@xyzmail.com",
                "some hash", "123-234-2349", true, true);
        UserDto testUserDto2 = new UserDto(3L, "Jake", "Hill", "jhill@xyzmail.com",
                "some hash", "456-753-1546", true, true);

        when(userService.getAllUsers()).thenReturn(Flux.just(testUser, testUser2));
        when(dtoConverter.convert(testUser)).thenReturn(testUserDto);
        when(dtoConverter.convert(testUser2)).thenReturn(testUserDto2);

        // Act
        Flux<UserDto> result = userController.getAllUsers();

        // Assert
        StepVerifier.create(result)
                .expectNext(testUserDto)
                .expectNext(testUserDto2)
                .verifyComplete();

        verify(userService, times(1)).getAllUsers();
        verify(dtoConverter, times(2)).convert(any(User.class));
    }

    @Test
    @DisplayName("GET /users - Should return empty Flux when no users exist")
    void testGetUserById_ShouldReturnEmptyFlux_whenNoUsersExist() {
        // Arrange
        when(userService.getUserById(any())).thenReturn(Mono.empty());

        // Act
        Mono<UserDto> result = userController.getUserById(testUser.getId());

        // Assert
        StepVerifier.create(result)
            .verifyComplete();

        verify(userService, times(1)).getUserById(anyLong());
        verify(dtoConverter, never()).convert(any(User.class));
    }

    @Test
    @DisplayName("GET /users/{id} - Should return user when found")
    void testGetUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        when(userService.getUserById(anyLong())).thenReturn(Mono.just(testUser));
        when(dtoConverter.convert(testUser)).thenReturn(testUserDto);

        // Act
        Mono<UserDto> result = userController.getUserById(TEST_ID);

        // Assert
        StepVerifier.create(result)
            .expectNext(testUserDto)
            .verifyComplete();

        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    @DisplayName("GET /users/{id} - Should return empty mono when user not found")
    void testGetUserById_ShouldReturnEmpty_WhenUserNotFound() {
        // Arrange
        when(userService.getUserById(anyLong())).thenReturn(Mono.empty());

        // Act
        Mono<UserDto> result = userController.getUserById(TEST_ID);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(userService, times(1)).getUserById(anyLong());
        verify(dtoConverter, never()).convert(any(User.class));
    }

    @Test
    @DisplayName("POST /users - Should create user successfully")
    void testCreateUser_ShouldCreateUser_WhenValidUserProvided() {
        // Arrange
        UserDto user1Dto = new UserDto("Arthas", "Menethil", "amenethil@xyzmail.com",
                "1234hash", "111-222-3333", true, true);

        User convertedUser = new User("Arthas", "Menethil", "amenethil@xyzmail.com",
                "1234hash", "111-222-3333", true, true);

        User savedUser = new User(5L, "Arthas", "Menethil", "amenethil@xyzmail.com",
                "1234hash", "111-222-3333", true, true);

        UserDto savedUserDto = new UserDto(5L, "Arthas", "Menethil",
                "amenethil@xyzmail.com", "1234hash", "111-222-3333",
                true, true);

        when(userConverter.convert(user1Dto)).thenReturn(convertedUser);
        when(userService.saveUser(convertedUser)).thenReturn(Mono.just(savedUser));
        when(dtoConverter.convert(savedUser)).thenReturn(savedUserDto);

        // Act
        Mono<UserDto> result = userController.createUser(user1Dto);

        // Assert
        StepVerifier.create(result)
            .expectNext(savedUserDto)
            .verifyComplete();

        verify(userService, times(1)).saveUser(any(User.class));
        verify(dtoConverter, times(1)).convert(any(User.class));
        verify(userConverter, times(1)).convert(any(UserDto.class));
    }

    @Test
    @DisplayName("POST /users - Should handle error when creation fails")
    void testCreateUser_ShouldHandleError_WhenCreationFails() {
        // Arrange
        UserDto inputUserDto = new UserDto();
        User inputUser = new User();
        RuntimeException exception = new RuntimeException("Creation failed");

        when(userConverter.convert(inputUserDto)).thenReturn(inputUser);
        when(userService.saveUser(any(User.class))).thenReturn(Mono.error(exception));

        // Act
        Mono<UserDto> result = userController.createUser(inputUserDto);

        // Assert
        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    @Test
    @DisplayName("POST /users/{id} - Should update user when user exists")
    void testUpdateUser_ShouldUpdateUser_WhenUserExists() {
        // Arrange
        UserDto userDto = new UserDto(TEST_ID, "Alton", "Esther", "aesther@xyzmail.com",
                "somehash", "111-222-3333", true, true);

        User user = new User(TEST_ID, "Alton", "Esther", "aesther@xyzmail.com",
                "somehash", "111-222-3333", true, true);

        User updatedInfoUser = new User(TEST_ID, "Alton", "Esther", "aesther@xyzmail.com",
                "somehash", "214-420-1958", true, true);

        UserDto updatedInfoUserDto = new UserDto(TEST_ID, "Alton", "Esther",
                "aesther@xyzmail.com", "somehash", "214-420-1958",
                true, true);

        when(userConverter.convert(userDto)).thenReturn(user);
        when(userService.updateUser(any(User.class))).thenReturn(Mono.just(updatedInfoUser));
        when(dtoConverter.convert(updatedInfoUser)).thenReturn(updatedInfoUserDto);

        // Act
        Mono<UserDto> result = userController.updateUser(userDto);

        // Assert
        StepVerifier.create(result)
            .assertNext(newUserDto -> {
                assertThat(newUserDto.getPhoneNumber()).isEqualTo("214-420-1958");
                assertThat(newUserDto.getId()).isEqualTo(TEST_ID);
            })
            .verifyComplete();

        verify(userConverter, times(1)).convert(userDto);
        verify(dtoConverter, times(1)).convert(updatedInfoUser);
        verify(userService, times(1)).updateUser(any(User.class));
    }

    @Test
    @DisplayName("POST /users/{id} - Should handle when user not found for update")
    void testUpdateUser_ShouldHandleNotFound_WhenUserDoesNotExist() {
        // Arrange
        UserDto userDto = new UserDto(TEST_ID, "Alton", "Esther", "aesther@xyzmail.com",
                "somehash", "111-222-3333", true, true);

        User user = new User(TEST_ID, "Alton", "Esther", "aesther@xyzmail.com",
                "somehash", "111-222-3333", true, true);

        RuntimeException exception = new RuntimeException("User does not exist");

        when(userConverter.convert(userDto)).thenReturn(user);
        when(userService.updateUser(any(User.class))).thenReturn(Mono.<User>error(exception));

        // Act
        Mono<UserDto> result = userController.updateUser(userDto);

        // Assert
        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();

        verify(userConverter, times(1)).convert(userDto);
        verify(userService, times(1)).updateUser(any(User.class));
        verify(dtoConverter, never()).convert(any(User.class));
    }

    @Test
    @DisplayName("POST /users/{id} - Should handle error during update")
    void testUpdateUser_ShouldHandleError_WhenUpdateFails() {
        // Arrange
        UserDto userDto = new UserDto(TEST_ID, "Alton", "Esther", "aesther@xyzmail.com",
                "somehash", "111-222-3333", true, true);

        User user = new User(TEST_ID, "Alton", "Esther", "aesther@xyzmail.com",
                "somehash", "111-222-3333", true, true);

        RuntimeException databaseException = new RuntimeException("Database connection failed");

        when(userConverter.convert(userDto)).thenReturn(user);
        when(userService.updateUser(any(User.class))).thenReturn(Mono.error(databaseException));

        // Act
        Mono<UserDto> result = userController.updateUser(userDto);

        // Assert
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        // Verify interactions
        verify(userConverter, times(1)).convert(userDto);
        verify(userService, times(1)).updateUser(user);
        verify(dtoConverter, never()).convert(any(User.class));
    }

    @Test
    @DisplayName("DELETE /users/{id} - Should delete user when user exists")
    void testDeleteUser_ShouldDeleteUser_WhenUserExists() {
        // Arrange
        when(userService.deleteUserById(TEST_ID)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = userController.deleteUser(TEST_ID);

        // Assert
        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(userService, times(1)).deleteUserById(TEST_ID);
    }
}