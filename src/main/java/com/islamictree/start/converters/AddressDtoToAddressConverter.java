package com.islamictree.start.converters;

import com.islamictree.start.dto.AddressDto;
import com.islamictree.start.models.Address;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AddressDtoToAddressConverter implements Converter<AddressDto, Address> {

    @Override
    public Address convert(AddressDto source) {
        return new Address(
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
