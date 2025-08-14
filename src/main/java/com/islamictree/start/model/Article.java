package com.islamictree.start.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="articles")
public class Article extends BaseEntity {

    private String title;

    @Column("blurb")
    private String blurb;

    @Column("article")
    private String article;

    @Column("image_data")
    private byte[] imageData;

    @Column("content_type")
    private String contentType;

    @CreatedDate
    @Column("created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column("last_updated")
    private Timestamp lastUpdated;

    public Article(Long id, String title, String blurb, String article,
                   byte[] imageData, String contentType) {
        super(id);
        this.title = title;
        this.blurb = blurb;
        this.article = article;
        this.imageData = imageData;
        this.contentType = contentType;
        this.createdAt = Timestamp.from(Instant.now());
        this.lastUpdated = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Article article1 = (Article) o;
        return Objects.equals(title, article1.title) && Objects.equals(blurb, article1.blurb) && Objects.equals(article, article1.article) && Arrays.equals(imageData, article1.imageData) && Objects.equals(contentType, article1.contentType) && Objects.equals(createdAt, article1.createdAt) && Objects.equals(lastUpdated, article1.lastUpdated);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(blurb);
        result = 31 * result + Objects.hashCode(article);
        result = 31 * result + Arrays.hashCode(imageData);
        result = 31 * result + Objects.hashCode(contentType);
        result = 31 * result + Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(lastUpdated);
        return result;
    }
}
