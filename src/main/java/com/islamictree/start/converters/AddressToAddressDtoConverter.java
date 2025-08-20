package com.islamictree.start.converters;

import com.islamictree.start.dto.AddressDto;
import com.islamictree.start.models.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class AddressToAddressDtoConverter implements Converter<Address, AddressDto> {

    @Override
    public AddressDto convert(Address source) {
        return new AddressDto(
            source.getId(),
            source.getStreet(),
            source.getCity(),
            source.getState(),
            source.getCountry(),
            source.getZipCode(),
            source.getLongitude(),
            source.getLatitude(),
            source.getIsValidated(),
            source.getAddressType(),
            source.getCreatedAt(),
            source.getUpdatedAt()

        );
    }
}
