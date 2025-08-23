package com.islamictree.start.services;

import com.islamictree.start.models.User;
import com.islamictree.start.repositories.UserRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService unit tests")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Testing createUser - Should create a user")
    void testSaveOrUpdateUser_ShouldCreateUser_WhenNoDuplicateExists() {
        // Arrange
        User user = new User("John", "Doe", "jdoe@xyz.com",
                "some random hash", "111-222-3333", null,
                true, true);

        User savedUser = new User(1L, "John", "Doe", "jdoe@xyz.com",
                "some random hash", "111-222-3333", null,
                true, true);

        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(user)).thenReturn(Mono.just(savedUser));

        // Act
        Mono<User> result = userService.saveOrUpdateUser(user);

        // Assert
        StepVerifier.create(result)
                .expectNext(savedUser)
                .verifyComplete();

        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Testing createUser - Should update user when email is in DB")
    void testSaveOrUpdateUser_ShouldUpdateUser_WhenEmailExists() {
        // Arrange
        User existingUser = new User(1L, "John", "Doe",
                "jdoe@xyz.com", "some random hash",
                "111-222-3333", null, true, true);

        User updateUser = new User("John", "Doe", "jdoe@xyz.com",
                "some random hash", "245-678-9012", null,
                true, true);

        User expectedUpdatedUser = new User(1L, "John", "Doe",
                "jdoe@xyz.com", "some random hash",
                "245-678-9012", null, true, true);


        when(userRepository.findByEmail(existingUser.getEmail()))
                .thenReturn(Mono.just(existingUser));
        when(userRepository.save(existingUser))
                .thenReturn(Mono.just(expectedUpdatedUser));

        // Act
        Mono<User> result = userService.saveOrUpdateUser(updateUser);

        // Assert
        StepVerifier.create(result)
                .expectNext(expectedUpdatedUser)
                .verifyComplete();

        verify(userRepository, times(1))
                .findByEmail(existingUser.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    @DisplayName("Testing getAllUsers - Should return all users")
    void testGetAllUsers_ShouldReturnAllUsers() {
        // Arrange
        User user1 = new User(1L, "John", "Doe",
            "jdoe@xyz.com", "some random hash",
            "111-222-3333", null, true, true);
        User user2 = new User(2L, "John", "David",
            "jdavid@xyz.com", "some random hash",
            "456-789-3219", null, true, true);
        User user3 = new User(3L, "Jeremy", "Dumbledore",
            "jdumbledore@xyz.com", "some random hash",
            "456-789-9513", null, true, true);

        when(userRepository.findAll()).thenReturn(Flux.just(user1, user2, user3));

        // Act
        Flux<User> users = userService.getAllUsers();

        // Assert
        assertThat(users).isNotNull();

        StepVerifier.create(users)
                .expectNext(user1, user2, user3)
                .verifyComplete();
    }

    @Test
    @DisplayName("Testing getUserById - Should return user by id")
    void testGetUserById_ShouldReturnUserById() {
        // Arrange
        Long userId = 1L;

        User user1 = new User(userId, "John", "Doe",
            "jdoe@xyz.com", "some random hash",
            "111-222-3333", null, true, true);

        when(userRepository.findById(anyLong())).thenReturn(Mono.just(user1));

        // Act
        Mono<User> existingUser = userService.getUserById(userId);

        StepVerifier.create(existingUser)
            .expectNext(user1)
            .verifyComplete();
    }

    @Test
    @DisplayName("Testing getUserById - Should return user by id")
    void testGetUserById_ShouldReturnError_WhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        RuntimeException findError = new RuntimeException("Database connection error");

        when(userRepository.findById(anyLong())).thenReturn(Mono.error(findError));

        // Act & Assert
        StepVerifier.create(userService.getUserById(userId))
            .expectError(RuntimeException.class)
            .verify();
    }


    @Test
    @DisplayName("Testing deleteUser - Should remove user from DB")
    void deleteUser() {
        // Arrange
        User user = new User(1L, "John", "Doe",
            "jdoe@xyz.com", "some random hash",
            "111-222-3333", null, true, true);

        when(userRepository.findById(anyLong())).thenReturn(Mono.just(user));
        when(userRepository.delete(any(User.class))).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = userService.deleteUserById(1L);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).delete(any(User.class));
    }
}