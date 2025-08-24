package com.islamictree.start.converters;

import com.islamictree.start.dto.UserDto;
import com.islamictree.start.models.User;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        if (source == null) {
            return null;
        }

        return new UserDto(
            source.getId(),
            source.getFirstName(),
            source.getLastName(),
            source.getEmail(),
            source.getPasswordHash(),
            source.getPhone(),
            source.getAddressId(),
            source.getIsActive(),
            source.getEmailVerified(),
            source.getCreatedAt(),
            source.getUpdatedAt()
        );
    }
}
