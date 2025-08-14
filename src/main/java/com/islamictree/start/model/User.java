package com.islamictree.start.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Table(name="users")
public class User extends Person {

    @Column("telephone")
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
