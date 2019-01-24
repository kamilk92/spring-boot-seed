package pl.kkp.core.util.date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.bean.LocalDateTimeParserConfiguration;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestLocalDateTimeParser extends SpringBootBaseTest {

    @Autowired
    private LocalDateTimeParser localDateTimeParser;

    private DateTimeFormatter dateTimeFormatter;

    public TestLocalDateTimeParser() {
        dateTimeFormatter = DateTimeFormatter.ofPattern(LocalDateTimeParserConfiguration.DATE_FMT);
    }

    @Test
    public void isParserDateTime() {
        String dateTimeValue = "24-01-2019 20:03:00";

        LocalDateTime parsedDateTime = localDateTimeParser.parse(dateTimeValue);
        assertThat(parsedDateTime).isNotNull();
        String reversedDateTime = localDateTimeToStr(parsedDateTime);
        assertThat(reversedDateTime).isEqualTo(dateTimeValue);
    }

    private String localDateTimeToStr(LocalDateTime dateTime) {
        return dateTime.format(dateTimeFormatter);
    }
}
