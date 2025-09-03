package com.islamictree.start.config;

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
public class R2dbcConversions {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = Arrays.asList(
                new ZonedDateTimeToTimestampConverter(),
                new TimestampToZonedDateTimeConverter()
        );

        return new R2dbcCustomConversions(CustomConversions.StoreConversions.NONE, converters);
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
