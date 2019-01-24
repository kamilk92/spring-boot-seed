package pl.kkp.core.testing.asserations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DateAssertions {
    public static void assertLocalDatetimeWithDate(LocalDateTime dateTime, Date date) {
        Timestamp seasonTimestamp = Timestamp.valueOf(dateTime);
        Timestamp seasonModelTimestamp = new Timestamp(date.getTime());
        assertThat(seasonTimestamp.getTime()).isEqualTo(seasonModelTimestamp.getTime());
    }
}
