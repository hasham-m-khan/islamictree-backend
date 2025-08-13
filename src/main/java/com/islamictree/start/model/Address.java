package com.islamictree.start.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="address")
public class Address extends BaseEntity {

    @Column("street_name")
    private String streetName;

    @Column("city")
    private String city;

    @Column("state")
    private String state;

    @Column("zip_code")
    private String zipCode;

    @Column("longitude")
    private Double longitude;

    @Column("latitude")
    private Double latitude;

    public Address(Long id, String streetName, String city,
                   String state, String zipCode, Double longitude,
                   Double latitude) {
        super(id);
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
