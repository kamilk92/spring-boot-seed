package pl.kkp.core.util.date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.bean.DateParserConfiguration;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestDateParser extends SpringBootBaseTest {

    @Autowired
    private DateParser dateParser;

    @Test
    public void isParserDate() throws ParseException {
        String strDate = "24-01-2019 14:56:00";

        Date parsedDate = dateParser.parse(strDate);

        assertThat(parsedDate).isNotNull();
        String reversedStrDate = dateToStr(parsedDate);
        assertThat(reversedStrDate).isEqualTo(strDate);
    }

    private String dateToStr(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DateParserConfiguration.DATE_FMT);

        return dateFormat.format(date);
    }
}
