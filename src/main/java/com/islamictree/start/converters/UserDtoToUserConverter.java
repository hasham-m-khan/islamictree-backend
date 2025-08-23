package com.islamictree.start.converters;

import com.islamictree.start.dto.UserDto;
import com.islamictree.start.models.User;
import org.springframework.core.convert.converter.Converter;

public class UserDtoToUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto source) {
        if (source == null) {
            return null;
        }

        return new User(
            source.getId(),
            source.getFirstName(),
            source.getLastName(),
            source.getEmail(),
            source.getPasswordHash(),
            source.getPhoneNumber(),
            source.getAddressId(),
            source.getIsActive(),
            source.getEmailVerified()
        );
    }
}
