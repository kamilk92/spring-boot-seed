package pl.kkp.core.controller.mapping;

import org.dozer.DozerConverter;
import org.springframework.stereotype.Component;
import pl.kkp.core.util.date.DateToLocalDateTimeConverter;
import pl.kkp.core.util.date.LocalDateTimeToDateConverter;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DozerDateToLocalDateTimeConverter extends DozerConverter<Date, LocalDateTime> {

    private DateToLocalDateTimeConverter dateToLocalDateTimeConverter;
    private LocalDateTimeToDateConverter localDateTimeToDateConverter;

    public DozerDateToLocalDateTimeConverter() {
        super(Date.class, LocalDateTime.class);
        dateToLocalDateTimeConverter = new DateToLocalDateTimeConverter();
        localDateTimeToDateConverter = new LocalDateTimeToDateConverter();
    }

    @Override
    public LocalDateTime convertTo(Date source, LocalDateTime destination) {
        return (source == null) ? null : dateToLocalDateTimeConverter.convert(source);
    }

    @Override
    public Date convertFrom(LocalDateTime source, Date destination) {
        return (source == null) ? null : localDateTimeToDateConverter.convert(source);
    }
}
