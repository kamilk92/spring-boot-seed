package pl.kkp.core.controller.mapping;

import org.dozer.DozerConverter;

import java.time.LocalDateTime;

public class DozerLocalDateTimeToLocalDateTimeConverter extends DozerConverter<LocalDateTime, LocalDateTime> {
    public DozerLocalDateTimeToLocalDateTimeConverter() {
        super(LocalDateTime.class, LocalDateTime.class);
    }

    @Override
    public LocalDateTime convertTo(LocalDateTime source, LocalDateTime destination) {
        return source;
    }

    @Override
    public LocalDateTime convertFrom(LocalDateTime source, LocalDateTime destination) {
        return source;
    }
}
