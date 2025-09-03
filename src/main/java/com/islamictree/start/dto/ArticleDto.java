package com.islamictree.start.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String blurb;
    private String content;
    private String source;
    private LocalDate datePublished;
    private String imageUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<AuthorDto> authors;
    private List<TagDto> tags;

    public ArticleDto(Long id, String title, String blurb, String content, String source,
                      LocalDate datePublished, String imageUrl, Timestamp createdAt,
                      Timestamp updatedAt, List<TagDto> tags) {
        this.id = id;
        this.title = title;
        this.blurb = blurb;
        this.content = content;
        this.source = source;
        this.datePublished = datePublished;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tags = tags;
    }
}
