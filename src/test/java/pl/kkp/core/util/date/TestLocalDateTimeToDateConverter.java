package pl.kkp.core.util.date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static pl.kkp.core.testing.asserations.DateAssertions.assertLocalDatetimeWithDate;

public class TestLocalDateTimeToDateConverter extends SpringBootBaseTest {

    @Autowired
    private LocalDateTimeToDateConverter localDateTimeToDateConverter;

    @Test
    public void isConvertLocalDateTimeToDate() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Date convertedDate = localDateTimeToDateConverter.convert(localDateTime);

        assertThat(convertedDate).isNotNull();
        assertLocalDatetimeWithDate(localDateTime, convertedDate);
    }

}
