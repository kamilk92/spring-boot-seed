package pl.kkp.core.util.date;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestDateToLocalDateTimeConverter {

    private static final String INPUT_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    private DateToLocalDateTimeConverter dateToLocalDateTimeConverter;

    public TestDateToLocalDateTimeConverter() {
        this.dateToLocalDateTimeConverter = new DateToLocalDateTimeConverter();
    }

    @Test
    public void isConvertDateToLocalDateTime() throws ParseException {
        String dateValue = "24-01-2019 14:34:00";
        Date date = createDateFromString(dateValue);

        LocalDateTime convertedDate = dateToLocalDateTimeConverter.convert(date);

        assertThat(convertedDate).isNotNull();
        Timestamp convertedDateTimestamp = Timestamp.valueOf(convertedDate);
        Timestamp originalDateTimestamp = new Timestamp(date.getTime());
        assertThat(convertedDateTimestamp.getTime())
                .isEqualTo(originalDateTimestamp.getTime());
    }

    private Date createDateFromString(String strDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(INPUT_DATE_FORMAT);

        return dateFormat.parse(strDate);
    }

}
