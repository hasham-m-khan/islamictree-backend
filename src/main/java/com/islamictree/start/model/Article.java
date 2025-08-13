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
    private Timestamp updatedAt;
}
