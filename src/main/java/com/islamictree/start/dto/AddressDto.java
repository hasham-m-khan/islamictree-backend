package com.islamictree.start.dto;

import com.islamictree.start.models.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private Double Longitude;
    private Double latitude;
    private Boolean isValidated;
    private AddressType addressType;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
