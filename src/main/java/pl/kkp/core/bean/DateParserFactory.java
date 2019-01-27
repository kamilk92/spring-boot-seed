package pl.kkp.core.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.util.date.DateParser;

@Configuration
public class DateParserFactory {
    public static final String DATE_FMT = "dd-MM-yyyy HH:mm:ss";

    @Bean
    public DateParser dateParser() throws Exception {
        return new DateParser(DATE_FMT);
    }
}
