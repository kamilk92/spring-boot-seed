package pl.kkp.core.util.date;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DateToLocalDateTimeConverter {
    public LocalDateTime convert(Date date) {
        long timestamp = date.getTime();
        Timestamp sqlTimestamp = new Timestamp(timestamp);

        return sqlTimestamp.toLocalDateTime();
    }
}
