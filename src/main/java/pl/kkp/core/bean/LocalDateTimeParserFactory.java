package pl.kkp.core.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.util.date.LocalDateTimeParser;

@Configuration
public class LocalDateTimeParserFactory {
    public static final String DATE_FMT = "dd-MM-yyyy HH:mm:ss";

    @Bean
    public LocalDateTimeParser localDateTimeParser() throws Exception {
        return new LocalDateTimeParser(DATE_FMT);
    }
}
