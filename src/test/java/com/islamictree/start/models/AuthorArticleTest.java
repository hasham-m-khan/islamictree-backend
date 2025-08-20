package com.islamictree.start.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorArticleTest {

    Long AUTHOR_ID;
    Long ARTICLE_ID;

    @BeforeEach
    void setUp() {
        AUTHOR_ID = 1L;
        ARTICLE_ID = 3L;
    }

    @Test
    @DisplayName("Test default constructor and setters for AuthorArticle")
    void testDefaultConstructorAndSetters() {
        // Arrange
        AuthorArticle authorArticle = new AuthorArticle();

        // Act
        authorArticle.setAuthorId(AUTHOR_ID);
        authorArticle.setArticleId(ARTICLE_ID);

        // Assert
        assertNotNull(authorArticle);
        assertEquals(AUTHOR_ID, authorArticle.getAuthorId());
        assertEquals(ARTICLE_ID, authorArticle.getArticleId());
    }

    @Test
    @DisplayName("Test All-arguments constructor for AuthorArticle")
    void testAllArgumentsConstructor() {
        // Act
        AuthorArticle authorArticle = new AuthorArticle(AUTHOR_ID, ARTICLE_ID);

        // Assert
        assertNotNull(authorArticle);
        assertEquals(AUTHOR_ID, authorArticle.getAuthorId());
        assertEquals(ARTICLE_ID, authorArticle.getArticleId());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for AuthorArticle")
    void testEqualsAndHashcode() {
        // Act
        AuthorArticle authorArticle1 = new AuthorArticle(AUTHOR_ID, ARTICLE_ID);
        AuthorArticle authorArticle2 = new AuthorArticle(AUTHOR_ID, ARTICLE_ID);
        AuthorArticle authorArticle3 = new AuthorArticle(5L, 3L);

        // Assert
        assertEquals(authorArticle1, authorArticle2);
        assertEquals(authorArticle1.hashCode(), authorArticle2.hashCode());
        assertNotEquals(authorArticle1, authorArticle3);
        assertNotEquals(authorArticle1.hashCode(), authorArticle3.hashCode());
    }
}