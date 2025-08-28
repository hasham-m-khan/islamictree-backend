package com.islamictree.start.services;

import com.islamictree.start.models.Author;
import com.islamictree.start.repositories.AuthorRepository;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorService unit tests")
class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    private Author author1;
    private Author author2;
    private Author savedAuthor1;
    private Author savedAuthor2;

    private Long SAVED_AUTHOR_ID_1 = 1L;
    private Long SAVED_AUTHOR_ID_2 = 2L;

    @BeforeEach
    void setUp() {
        author1 = new Author();
        author2 = new Author();
        savedAuthor1 = new Author();
        savedAuthor2 = new Author();

        author1.setFirstName("John");
        author1.setLastName("Doe");
        author2.setFirstName("Jane");
        author2.setLastName("Doe");

        savedAuthor1.setFirstName("John");
        savedAuthor1.setLastName("Doe");
        savedAuthor1.setId(SAVED_AUTHOR_ID_1);
        savedAuthor2.setFirstName("Jane");
        savedAuthor2.setLastName("Doe");
        savedAuthor2.setId(SAVED_AUTHOR_ID_2);
    }

    @Test
    @DisplayName("Testing getAllAuthors - Should return all authors")
    void testGetAllAuthors_ShouldReturnAllAuthors() {
        // Arrange
        Flux<Author> savedAuthors = Flux.just(savedAuthor1, savedAuthor2);
        when(authorRepository.findAll()).thenReturn(savedAuthors);

        // Act
        Flux<Author> result = authorService.getAllAuthors();

        // Assert
        StepVerifier.create(result)
                .expectNext(savedAuthor1, savedAuthor2)
                .verifyComplete();

        verify(authorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Testing getAuthorById - Should return author when id exists")
    void testGetAuthorById_ShouldReturnAuthor_WhenIdExists() {
        // Arrange
        when(authorRepository.findById(SAVED_AUTHOR_ID_1)).thenReturn(Mono.just(savedAuthor1));

        // Act
        Mono<Author> result = authorService.getAuthorById(SAVED_AUTHOR_ID_1);

        // Assert
        StepVerifier.create(result)
            .expectNext(savedAuthor1)
            .verifyComplete();

        verify(authorRepository, times(1)).findById(SAVED_AUTHOR_ID_1);
    }

    @Test
    @DisplayName("Testing getAuthorById - Should return Empty when id not found")
    void testGetAuthorById_ShouldReturnEmpty_WhenIdNotFound() {
        // Arrange
        when(authorRepository.findById(SAVED_AUTHOR_ID_2)).thenReturn(Mono.empty());

        // Act
        Mono<Author> result = authorService.getAuthorById(SAVED_AUTHOR_ID_2);

        // Assert
        StepVerifier.create(result)
            .verifyComplete();

        verify(authorRepository, times(1)).findById(SAVED_AUTHOR_ID_2);
    }

    @Test
    @DisplayName("Testing saveAuthor - Should save author")
    void testSaveAuthor_ShouldSaveAuthor() {
        // Arrange
        when(authorRepository.save(author1)).thenReturn(Mono.just(savedAuthor1));

        // Act
        Mono<Author> result = authorService.saveAuthor(author1);

        // Assert
        StepVerifier.create(result)
            .expectNext(savedAuthor1)
            .verifyComplete();

        verify(authorRepository, times(1)).save(author1);
    }

    //TODO: Test saveUser for required fields

    @Test
    @DisplayName("Testing updateAuthor - Should update author when id exists")
    void testUpdateAuthor_ShouldUpdateAuthor_WhenIdExists() {
        // Arrange
        Author updated = new Author();
        updated.setFirstName("John");
        updated.setLastName("Davidson");
        updated.setId(SAVED_AUTHOR_ID_1);

        when(authorRepository.findById(SAVED_AUTHOR_ID_1)).thenReturn(Mono.just(savedAuthor1));
        when(authorRepository.save(savedAuthor1)).thenReturn(Mono.just(updated));

        // Act
        Mono<Author> result = authorService.updateAuthor(savedAuthor1);

        // Assert
        StepVerifier.create(result)
            .expectNext(updated)
            .verifyComplete();

        verify(authorRepository, times(1)).findById(SAVED_AUTHOR_ID_1);
        verify(authorRepository, times(1)).save(savedAuthor1);
    }

    @Test
    @DisplayName("Testing getAuthorById - Should return Empty when id not found")
    void testUpdateAuthor_ShouldReturnEmpty_WhenIdNotFound() {
        // Arrange
        when(authorRepository.findById(SAVED_AUTHOR_ID_2)).thenReturn(Mono.empty());

        // Act
        Mono<Author> result = authorService.updateAuthor(savedAuthor2);

        // Assert
        StepVerifier.create(result)
            .verifyComplete();

        verify(authorRepository, times(1)).findById(SAVED_AUTHOR_ID_2);
    }

    @Test
    @DisplayName("Testing deleteAuthor - Should delete author when id exists")
    void testDeleteAuthor_ShouldDeleteAuthor_WhenIdExists() {
        // Arrange
        when(authorRepository.deleteById(SAVED_AUTHOR_ID_1)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = authorService.deleteAuthor(SAVED_AUTHOR_ID_1);

        // Assert
        StepVerifier.create(result)
            .verifyComplete();

        verify(authorRepository, times(1)).deleteById(SAVED_AUTHOR_ID_1);
    }
}

//TODO: Test deleteAuthor when id not found
