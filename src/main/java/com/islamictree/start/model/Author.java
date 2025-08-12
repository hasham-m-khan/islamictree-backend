package com.islamictree.start.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="authors")
public class Author extends BaseEntity {

    @OneToOne
    private User user;

    private String description;

    @ManyToMany(mappedBy = "authors")
    private Set<Article> articles = new HashSet<>();
}
