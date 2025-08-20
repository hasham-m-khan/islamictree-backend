package com.islamictree.start.models;

import com.islamictree.start.models.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="addresses")
public class Address extends BaseEntity {

    @Column("street")
    private String street;

    @Column("city")
    private String city;

    @Column("state")
    private String state;

    @Column("country")
    private String country;

    @Column("zip_code")
    private String zipCode;

    @Column("longitude")
    private Double longitude;

    @Column("latitude")
    private Double latitude;

    @Column("is_validated")
    private Boolean isValidated;

    @Column("address_type")
    private AddressType addressType;

    @Column("created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Timestamp updatedAt;


    public Address(Long id, String street, String city, String state,
                   String country, String zipCode, Double longitude, Double latitude,
                   Boolean isValidated,  AddressType addressType,
                   Timestamp createdAt, Timestamp updatedAt) {
        super(id);
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isValidated = isValidated;
        this.addressType = addressType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Address(String street, String city, String state, String country,
                   String zipCode, Double longitude, Double latitude,
                   Boolean isValidated, AddressType addressType) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isValidated = isValidated;
        this.addressType = addressType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Address address = (Address) o;
        return street.equals(address.street) &&
                city.equals(address.city) &&
                state.equals(address.state) &&
                zipCode.equals(address.zipCode) &&
                country.equals(address.country) &&
                Objects.equals(longitude, address.longitude) &&
                Objects.equals(latitude, address.latitude) &&
                Objects.equals(isValidated, address.isValidated) &&
                Objects.equals(addressType, address.addressType);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + zipCode.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + Objects.hashCode(longitude);
        result = 31 * result + Objects.hashCode(latitude);
        result = 31 * result + Objects.hashCode(isValidated);
        result = 31 * result + Objects.hashCode(addressType);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", isValidated=" + isValidated +
                ", addressType=" + addressType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
