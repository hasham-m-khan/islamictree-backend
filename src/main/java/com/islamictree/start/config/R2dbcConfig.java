package com.islamictree.start.config;

import com.islamictree.start.models.enums.AddressType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;


@Configuration
public class R2dbcConfig {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = Arrays.asList(
                new AddressTypeToStringConverter(),
                new StringToAddressTypeConverter(),
                new ZonedDateTimeToTimestampConverter(),
                new TimestampToZonedDateTimeConverter()
        );

        return new R2dbcCustomConversions(CustomConversions.StoreConversions.NONE, converters);
    }

    @WritingConverter
    static class AddressTypeToStringConverter implements Converter<AddressType, String> {
        @Override
        public String convert(AddressType source) {
            return source != null ? source.name() : null;
        }
    }

    @ReadingConverter
    static class StringToAddressTypeConverter implements Converter<String, AddressType> {
        @Override
        public AddressType convert(String source) {
            return source != null ? AddressType.valueOf(source) : null;
        }
    }

    @ReadingConverter
    static class ZonedDateTimeToTimestampConverter implements Converter<ZonedDateTime, Timestamp> {
        @Override
        public Timestamp convert(ZonedDateTime source) {
            return source != null ? Timestamp.from(source.toInstant()) : null;
        }
    }

    @WritingConverter
    static class TimestampToZonedDateTimeConverter implements Converter<Timestamp, ZonedDateTime> {
        @Override
        public ZonedDateTime convert(Timestamp source) {
            return source != null ? source.toInstant().atZone(ZoneId.systemDefault()) : null;
        }
    }
}
