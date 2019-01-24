package pl.kkp.core.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    private DateFormat dateFormat;

    public DateParser(String dateFormat) {
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }

    public Date parse(String value) throws ParseException {
        return dateFormat.parse(value);
    }
}
