package com.islamictree.start.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="address")
public class Address extends BaseEntity {

    @Column(name = "streetName")
    private String streetName;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "Latitude")
    private Double latitude;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "address")
    private Set<User> users;
}
