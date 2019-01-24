package pl.kkp.core.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.util.date.LocalDateTimeParser;

@Configuration
public class LocalDateTimeParserConfiguration {
    public static final String DATE_FMT = "dd-MM-yyyy HH:mm:ss";

    @Bean
    public LocalDateTimeParserFactory localDateTimeParserFactory() {
        return new LocalDateTimeParserFactory(DATE_FMT);
    }

    @Bean
    public LocalDateTimeParser localDateTimeParser(
            LocalDateTimeParserFactory localDateTimeParserFactory) throws Exception {
        return localDateTimeParserFactory.getObject();
    }
}
