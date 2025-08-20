package com.islamictree.start.models;

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
@Table(name="users")
public class User extends Person {

    @Column("email")
    private String email;

    @Column("password_hash")
    private String passwordHash;

    @Column("telephone")
    private String phone;

    @Column("address_id")
    private Long addressId;

    @Column("is_active")
    private Boolean isActive;

    @Column("email_verified")
    private Boolean emailVerified;

    @Column("created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Timestamp updatedAt;

    public User(Long id, String firstName, String lastName, String email,
                String passwordHash, String phone, Long addressId, Boolean isActive,
                Boolean emailVerified, Timestamp createdAt, Timestamp updatedAt) {
        super(id, firstName, lastName);
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.addressId = addressId;
        this.isActive = isActive;
        this.emailVerified = emailVerified;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(Long id, String firstName, String lastName, String email,
                String passwordHash, String phone, Long addressId,
                Boolean isActive, Boolean emailVerified) {
        super(id, firstName, lastName);
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.addressId = addressId;
        this.isActive = isActive;
        this.emailVerified = emailVerified;
    }

    public User(String firstName, String lastName, String email, String passwordHash,
                String phone, Boolean isActive, Boolean emailVerified) {
        super(firstName, lastName);
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.isActive = isActive;
        this.emailVerified = emailVerified;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;
        return Objects.equals(phone, user.phone) && Objects.equals(addressId, user.addressId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(phone);
        result = 31 * result + Objects.hashCode(addressId);
        return result;
    }
}
