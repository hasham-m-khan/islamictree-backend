package com.islamictree.start.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User extends Person {

    @Column(name = "telephone")
    private String phone;

    @ManyToOne
    private Address address;
}
