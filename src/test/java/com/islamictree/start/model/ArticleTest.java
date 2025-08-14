package com.islamictree.start.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    Long ID;
    String TITLE;
    String BLURB;
    String ARTICLE;
    byte[] IMAGE_DATA;
    String CONTENT_TYPE;
    Timestamp CREATED_AT;
    Timestamp LAST_UPDATED;

    @BeforeEach
    void setUp() {
        ID = 1L;
        TITLE = "Some Title";
        BLURB = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tellus.";
        ARTICLE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc suscipit ex vel purus iaculis, ut.";
        IMAGE_DATA = "some image data".getBytes();
        CONTENT_TYPE = "image/jpeg";
        CREATED_AT = Timestamp.from(Instant.now());
        LAST_UPDATED = Timestamp.from(Instant.now());
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
        article.setArticle(ARTICLE);
        article.setImageData(IMAGE_DATA);
        article.setContentType(CONTENT_TYPE);
        article.setCreatedAt(CREATED_AT);
        article.setLastUpdated(LAST_UPDATED);

        // Assert
        assertNotNull(article);
        assertEquals(ID, article.getId());
        assertEquals(TITLE, article.getTitle());
        assertEquals(BLURB, article.getBlurb());
        assertEquals(ARTICLE, article.getArticle());
        assertEquals(IMAGE_DATA, article.getImageData());
        assertEquals(CONTENT_TYPE, article.getContentType());
        assertEquals(CREATED_AT, article.getCreatedAt());
        assertEquals(LAST_UPDATED, article.getLastUpdated());
    }

    @Test
    @DisplayName("Test All-arguments constructor for Article")
    void testAllArgumentsConstructor() {
        // Act
        Article article = new Article(1L, TITLE, BLURB, ARTICLE,
                IMAGE_DATA, CONTENT_TYPE);

        // Assert
        assertNotNull(article);
        assertEquals(ID, article.getId());
        assertEquals(TITLE, article.getTitle());
        assertEquals(BLURB, article.getBlurb());
        assertEquals(ARTICLE, article.getArticle());
        assertEquals(IMAGE_DATA, article.getImageData());
        assertEquals(CONTENT_TYPE, article.getContentType());
        assertNotNull(article.getCreatedAt());
        assertNotNull(article.getLastUpdated());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for Article")
    void testEqualsAndHashcode() {
        // Arrange
        Article article1 = new Article(1L, TITLE, BLURB, ARTICLE,
                IMAGE_DATA, CONTENT_TYPE);
        Article article2 = new Article(1L, TITLE, BLURB, ARTICLE,
                IMAGE_DATA, CONTENT_TYPE);
        Article article3 = new Article(5L, TITLE, BLURB, ARTICLE,
                IMAGE_DATA, CONTENT_TYPE);

        // Assert
        assertEquals(article1, article2);
        assertEquals(article1.hashCode(), article2.hashCode());
        assertNotEquals(article1, article3);
        assertNotEquals(article1.hashCode(), article3.hashCode());
    }
}