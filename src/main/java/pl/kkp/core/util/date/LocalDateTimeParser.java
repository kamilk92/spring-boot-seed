package pl.kkp.core.util.date;


import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {
    private DateTimeFormatter dateTimeFormatter;

    public LocalDateTimeParser(String dateFormat) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    public LocalDateTime parse(String value) {
        return LocalDateTime.parse(value, dateTimeFormatter);
    }
}
