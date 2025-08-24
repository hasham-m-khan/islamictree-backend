package com.islamictree.start.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String phoneNumber;
    private Long addressId;
    private Boolean isActive;
    private Boolean emailVerified;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public UserDto(Long id, String firstName, String lastName, String email, String passwordHash,
                   String phoneNumber, Boolean isActive, Boolean emailVerified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.emailVerified = emailVerified;
    }

    public UserDto(String firstName, String lastName, String email, String passwordHash, String phoneNumber,
                   Long addressId, Boolean isActive, Boolean emailVerified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.addressId = addressId;
        this.isActive = isActive;
        this.emailVerified = emailVerified;
    }

    public UserDto(String firstName, String lastName, String email, String passwordHash, String phoneNumber,
                   Boolean isActive, Boolean emailVerified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.emailVerified = emailVerified;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addressId=" + addressId +
                ", isActive=" + isActive +
                ", emailVerified=" + emailVerified +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
