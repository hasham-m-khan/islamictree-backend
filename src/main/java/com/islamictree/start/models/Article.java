package com.islamictree.start.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="articles")
public class Article extends BaseEntity {

    @Column("title")
    private String title;

    @Column("blurb")
    private String blurb;

    @Column("content")
    private String content;

    @Column("source")
    private String source;

    @Column("date_published")
    private LocalDate datePublished;

    @Column("image_data")
    private byte[] imageData;

    @Column("mime_type")
    private String mimeType;

    @CreatedDate
    @Column("created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Timestamp updatedAt;

    public Article(Long id, String title, String blurb, String content, String source,
                   LocalDate datePublished, byte[] imageData, String mimeType) {
        super(id);
        this.title = title;
        this.blurb = blurb;
        this.content = content;
        this.source = source;
        this.datePublished = datePublished;
        this.imageData = imageData;
        this.mimeType = mimeType;
    }

    public Article(String title, String blurb, String content, String source, LocalDate datePublished,
                   byte[] imageData, String mimeType) {
        this.title = title;
        this.blurb = blurb;
        this.content = content;
        this.source = source;
        this.datePublished = datePublished;
        this.imageData = imageData;
        this.mimeType = mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Article article = (Article) o;
        return title.equals(article.title) && Objects.equals(blurb, article.blurb) && content.equals(article.content) && Objects.equals(source, article.source) && Objects.equals(datePublished, article.datePublished) && Arrays.equals(imageData, article.imageData) && Objects.equals(mimeType, article.mimeType) && Objects.equals(createdAt, article.createdAt) && Objects.equals(updatedAt, article.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + Objects.hashCode(blurb);
        result = 31 * result + content.hashCode();
        result = 31 * result + Objects.hashCode(source);
        result = 31 * result + Objects.hashCode(datePublished);
        result = 31 * result + Arrays.hashCode(imageData);
        result = 31 * result + Objects.hashCode(mimeType);
        result = 31 * result + Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(updatedAt);
        return result;
    }
}
