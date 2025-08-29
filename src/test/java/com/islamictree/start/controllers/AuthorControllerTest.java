package com.islamictree.start.controllers;

import com.islamictree.start.converters.AuthorDtoToAuthorConverter;
import com.islamictree.start.converters.AuthorToAuthorDtoConverter;
import com.islamictree.start.dto.AuthorDto;
import com.islamictree.start.models.Author;
import com.islamictree.start.services.AuthorService;
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

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;

    @Mock
    private AuthorToAuthorDtoConverter dtoConverter;

    @Mock
    private AuthorDtoToAuthorConverter authorConverter;

    Long TEST_ID = 1L;
    Author author;
    Author created;
    AuthorDto authorDto;
    AuthorDto returnedDto;

    @BeforeEach
    void setUp() {
        author = new Author("John", "Doe", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");

        created = new Author(TEST_ID, "John", "Doe", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");

        authorDto = new AuthorDto("John", "Doe", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");

        returnedDto = new AuthorDto(TEST_ID, "John", "Doe", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");
    }

    @Test
    @DisplayName("GET /authors - Should return all authors")
    void testGetAllAuthors_ShouldReturnAllAuthors() {
        // Arrange
        Author author1 = new Author(2L, "John", "Doe", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");
        Author author2 = new Author(3L, "Billy", "Bob", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");
        Author author3 = new Author(4L, "Dame", "Nill", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");

        AuthorDto author1Dto = new AuthorDto(2L, "John", "Doe", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");
        AuthorDto author2Dto = new AuthorDto(3L, "Billy", "Bob", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");
        AuthorDto author3Dto = new AuthorDto(4L, "Dame", "Nill", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");

        when(authorService.getAllAuthors()).thenReturn(Flux.just(author1, author2, author3));
        when(dtoConverter.convert(author1)).thenReturn(author1Dto);
        when(dtoConverter.convert(author2)).thenReturn(author2Dto);
        when(dtoConverter.convert(author3)).thenReturn(author3Dto);

        // Act
        Flux<AuthorDto> result = authorController.getAllAuthors(false);

        // Assert
        StepVerifier.create(result)
            .expectNext(author1Dto, author2Dto, author3Dto)
            .verifyComplete();

        verify(authorService, times(1)).getAllAuthors();
        verify(dtoConverter, times(3)).convert(any(Author.class));
    }

    @Test
    @DisplayName("GET /authors - Should handle repository errors")
    void testGetAuthorById_ShouldHandleError_WhenError() {
        // Arrange
        RuntimeException exception = new RuntimeException("Unknown error");

        when(authorService.getAuthorById(anyLong())).thenReturn(Mono.error(exception));

        // Act
        Mono<AuthorDto> result = authorController.getAuthorById(TEST_ID, false);

        // Assert
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(authorService, times(1)).getAuthorById(TEST_ID);
    }

    @Test
    @DisplayName("GET /authors - Should throw error when author not found")
    void testGetAuthorById_ShouldThrowError_WhenAuthorNotFound() {
        // Arrange
        RuntimeException exception = new RuntimeException("Author not found");

        when(authorService.getAuthorById(anyLong())).thenReturn(Mono.empty());

        // Act
        Mono<AuthorDto> result = authorController.getAuthorById(TEST_ID, false);

        // Assert
        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();

        verify(authorService, times(1)).getAuthorById(TEST_ID);
    }

    @Test
    @DisplayName("GET /authors - Should return author when found")
    void testGetAuthorById_ShouldReturnAuthor_WhenFound() {
        // Arrange
        when(authorService.getAuthorById(TEST_ID)).thenReturn(Mono.just(created));
        when(dtoConverter.convert(created)).thenReturn(returnedDto);

        // Act
        Mono<AuthorDto> result = authorController.getAuthorById(TEST_ID, false);

        // Assert
        StepVerifier.create(result)
            .expectNext(returnedDto)
            .verifyComplete();

        verify(authorService, times(1)).getAuthorById(TEST_ID);
        verify(dtoConverter, times(1)).convert(created);
    }

    @Test
    @DisplayName("POST /authors - Should save author when valid user")
    void testCreateAuthor_ShouldSaveAuthor_WhenValidUser() {
        // Arrange
        when(authorConverter.convert(authorDto)).thenReturn(author);
        when(authorService.saveAuthor(author)).thenReturn(Mono.just(created));
        when(dtoConverter.convert(created)).thenReturn(returnedDto);

        // Act
        Mono<AuthorDto> result = authorController.createAuthor(authorDto);

        // Assert
        StepVerifier.create(result)
            .expectNext(returnedDto)
            .verifyComplete();

        verify(authorService, times(1)).saveAuthor(author);
        verify(dtoConverter, times(1)).convert(created);
        verify(authorConverter, times(1)).convert(authorDto);
    }

    @Test
    @DisplayName("POST /authors - Should handle error when save fails")
    void testCreateAuthor_ShouldHandleError_WhenSaveFails() {
        // Arrange
        when(authorConverter.convert(authorDto)).thenReturn(author);
        when(authorService.saveAuthor(author)).thenReturn(Mono.error(new RuntimeException("*** Error saving author")));

        // Act
        Mono<AuthorDto> result = authorController.createAuthor(authorDto);

        // Assert
        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();

        verify(authorService, times(1)).saveAuthor(author);
        verify(authorConverter, times(1)).convert(authorDto);
        verify(dtoConverter, never()).convert(created);
    }

    @Test
    @DisplayName("PUT /authors - Should update author on valid author")
    void testUpdateAuthor_ShouldUpdateAuthor_WhenValidAuthor() {
        // Arrange
        AuthorDto updatedDto = new AuthorDto(TEST_ID, "Jeremy", "DeAngelo", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");

        Author updated = new Author(TEST_ID, "Jeremy", "DeAngelo", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");

        when(authorConverter.convert(updatedDto)).thenReturn(updated);
        when(authorService.updateAuthor(updated)).thenReturn(Mono.just(updated));
        when(dtoConverter.convert(updated)).thenReturn(updatedDto);

        // Act
        Mono<AuthorDto> result = authorController.updateAuthor(updatedDto);

        // Assert
        StepVerifier.create(result)
                .expectNext(updatedDto)
                .verifyComplete();

        verify(authorService, times(1)).updateAuthor(updated);
        verify(dtoConverter, times(1)).convert(updated);
        verify(authorConverter, times(1)).convert(updatedDto);
    }

    @Test
    @DisplayName("PUT /authors - Should throw error when update fails")
    void testUpdateAuthor_ShouldThrowError_WhenUpdateFails() {
        // Arrange
        AuthorDto updatedDto = new AuthorDto(TEST_ID, "Jeremy", "DeAngelo", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");

        Author updated = new Author(TEST_ID, "Jeremy", "DeAngelo", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");

        RuntimeException exception = new RuntimeException("*** Error updating author");

        when(authorConverter.convert(updatedDto)).thenReturn(updated);
        when(authorService.updateAuthor(updated)).thenReturn(Mono.error(exception));

        // Act
        Mono<AuthorDto> result = authorController.updateAuthor(updatedDto);

        // Assert
        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();

        verify(authorService, times(1)).updateAuthor(updated);
        verify(authorConverter, times(1)).convert(any(AuthorDto.class));
        verify(dtoConverter, never()).convert(any(Author.class));
    }

    @Test
    @DisplayName("PUT /authors - Should throw error when id not found")
    void testUpdateAuthor_ShouldThrowError_WhenIdNotFound() {
        // Arrange
        AuthorDto updatedDto = new AuthorDto("Jeremy", "DeAngelo", LocalDate.parse("1998-02-25"),
                LocalDate.parse("2022-04-13"), "New York", "California",
                "Some description", "some data".getBytes(), "image/jpg");

        // Act
        Mono<AuthorDto> result = authorController.updateAuthor(updatedDto);

        // Assert
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(authorService, never()).updateAuthor(any(Author.class));
        verify(authorConverter, never()).convert(any(AuthorDto.class));
        verify(dtoConverter, never()).convert(any(Author.class));
    }

    @Test
    @DisplayName("DELETE - /authors/{id} - Should delete author when valid id")
    void deleteAuthor_ShouldDeleteAuthor_WhenValidId() {
        // Arrange
        when(authorService.deleteAuthor(TEST_ID)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = authorController.deleteAuthor(TEST_ID);

        // Assert
        StepVerifier.create(result)
            .expectComplete()
            .verify();

        verify(authorService, times(1)).deleteAuthor(TEST_ID);
    }
}