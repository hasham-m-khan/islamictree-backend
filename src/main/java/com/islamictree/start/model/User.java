package com.islamictree.start.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name="users")
public class User extends Person {

    @Column("phone")
    private String phone;

    @Column("address_id")
    private Long addressId;

    public User(String firstName, String lastName, String phone, Long addressId) {
        super(firstName, lastName);
        this.phone = phone;
        this.addressId = addressId;
    }

    public User(Long id, String firstName, String lastName, String phone, Long addressId) {
        super(id, firstName, lastName);
        this.phone = phone;
        this.addressId = addressId;
    }
}
