package com.islamictree.start.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTagTest {

    Long ARTICLE_ID;
    Long TAG_ID;

    @BeforeEach
    void setUp() {
        ARTICLE_ID = 1L;
        TAG_ID = 2L;
    }

    @Test
    @DisplayName("Test default constructor and setters for ArticleTag")
    void testDefaultConstructorAndSetters() {
        // Arrange
        ArticleTag articleTag = new ArticleTag();

        // Act
        articleTag.setArticleId(ARTICLE_ID);
        articleTag.setTagId(TAG_ID);

        // Assert
        assertNotNull(articleTag);
        assertEquals(ARTICLE_ID, articleTag.getArticleId());
        assertEquals(TAG_ID, articleTag.getTagId());
    }

    @Test
    @DisplayName("Test All-arguments constructor for ArticleTag")
    void testAllArgumentsConstructor() {
        // Act
        ArticleTag articleTag = new ArticleTag(ARTICLE_ID, TAG_ID);

        // Assert
        assertNotNull(articleTag);
        assertEquals(ARTICLE_ID, articleTag.getArticleId());
        assertEquals(TAG_ID, articleTag.getTagId());
    }

    @Test
    @DisplayName("Test equals and hashcode methods for ArticleTag")
    void testEqualsAndHashcode() {
        // Arrange
        ArticleTag articleTag1 = new ArticleTag(ARTICLE_ID, TAG_ID);
        ArticleTag articleTag2 = new ArticleTag(ARTICLE_ID, TAG_ID);
        ArticleTag articleTag3 = new ArticleTag(400L, TAG_ID);

        // Assert
        assertEquals(articleTag1, articleTag2);
        assertEquals(articleTag1.hashCode(), articleTag1.hashCode());
        assertNotEquals(articleTag1, articleTag3);
        assertNotEquals(articleTag1.hashCode(), articleTag3.hashCode());
    }
}