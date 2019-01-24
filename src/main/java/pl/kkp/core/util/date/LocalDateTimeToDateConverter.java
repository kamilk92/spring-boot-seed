package pl.kkp.core.util.date;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class LocalDateTimeToDateConverter {
    public Date convert(LocalDateTime localDateTime) {
        Timestamp dateTimestamp = Timestamp.valueOf(localDateTime);
        long timestamp = dateTimestamp.getTime();

        return new Date(timestamp);
    }
}
