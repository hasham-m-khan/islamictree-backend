package com.islamictree.start.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    Long ID;
    String TITLE;
    String BLURB;
    String CONTENT;
    String SOURCE;
    LocalDate DATE_PUBLISHED;
    String IMAGE_URL;
    Timestamp CREATED_AT;
    Timestamp UPDATED_AT;

    @BeforeEach
    void setUp() {
        ID = 1L;
        TITLE = "Some Title";
        BLURB = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus.";
        SOURCE = "https://www.somesource.org/";
        DATE_PUBLISHED = LocalDate.of(2018, 1, 1);
        CONTENT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc suscipit ex vel purus iaculis, ut.";
        IMAGE_URL = "some image url";
        CREATED_AT = Timestamp.from(Instant.now());
        UPDATED_AT = Timestamp.from(Instant.now());
    }

    @Test
    @DisplayName("Test default constructor and setters for Article")
    void testDefaultConstructorAndSetters() {
        // Arrange
        Article article = new Article();

        // Act
        article.setId(ID);
        article.setTitle(TITLE);
        article.setBlurb(BLURB);
        article.setSource(SOURCE);
        article.setDatePublished(DATE_PUBLISHED);
        article.setContent(CONTENT);
        article.setImageUrl(IMAGE_URL);
        article.setCreatedAt(CREATED_AT);
        article.setUpdatedAt(UPDATED_AT);

        // Assert
        assertNotNull(article);
        assertEquals(ID, article.getId());
        assertEquals(TITLE, article.getTitle());
        assertEquals(BLURB, article.getBlurb());
        assertEquals(SOURCE, article.getSource());
        assertEquals(DATE_PUBLISHED, article.getDatePublished());
        assertEquals(CONTENT, article.getContent());
        assertEquals(IMAGE_URL, article.getImageUrl());
        assertEquals(CREATED_AT, article.getCreatedAt());
        assertEquals(UPDATED_AT, article.getUpdatedAt());
    }

    @Test
    @DisplayName("Test All-arguments constructor for Article")
    void testAllArgumentsConstructor() {
        // Act
        Article article = new Article(ID, TITLE, BLURB, CONTENT, SOURCE, DATE_PUBLISHED, IMAGE_URL);

        // Assert
        assertNotNull(article);
        assertEquals(ID, article.getId());
        assertEquals(TITLE, article.getTitle());
        assertEquals(BLURB, article.getBlurb());
        assertEquals(SOURCE, article.getSource());
        assertEquals(DATE_PUBLISHED, article.getDatePublished());
        assertEquals(CONTENT, article.getContent());
        assertEquals(IMAGE_URL, article.getImageUrl());
        assertNull(article.getCreatedAt());
        assertNull(article.getUpdatedAt());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Article")
    void testEqualsAndHashcode() {
        // Arrange
        Article article1 = new Article(ID, TITLE, BLURB, CONTENT, SOURCE, DATE_PUBLISHED, IMAGE_URL);
        Article article2 = new Article(ID, TITLE, BLURB, CONTENT, SOURCE, DATE_PUBLISHED, IMAGE_URL);
        Article article3 = new Article(234L, TITLE, BLURB, CONTENT, SOURCE, DATE_PUBLISHED, IMAGE_URL);

        // Assert
        assertEquals(article1, article2);
        assertEquals(article1.hashCode(), article2.hashCode());
        assertNotEquals(article1, article3);
        assertNotEquals(article1.hashCode(), article3.hashCode());
    }
}