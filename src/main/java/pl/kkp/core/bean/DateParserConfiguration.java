package pl.kkp.core.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.util.date.DateParser;

@Configuration
public class DateParserConfiguration {
    public static final String DATE_FMT = "dd-MM-yyyy HH:mm:ss";

    @Bean
    public DateParserFactory dateParserFactory() {
        return new DateParserFactory(DATE_FMT);
    }

    @Bean
    public DateParser dateParser(DateParserFactory dateParserFactory) throws Exception {
        return dateParserFactory.getObject();
    }
}
