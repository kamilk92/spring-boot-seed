package pl.kkp.core.bean;

import org.springframework.beans.factory.FactoryBean;
import pl.kkp.core.util.date.LocalDateTimeParser;

public class LocalDateTimeParserFactory implements FactoryBean<LocalDateTimeParser> {
    private static final boolean IS_SINGLETON = true;

    private String dateFormat;

    public LocalDateTimeParserFactory(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public LocalDateTimeParser getObject() throws Exception {
        return new LocalDateTimeParser(dateFormat);
    }

    @Override
    public Class<?> getObjectType() {
        return LocalDateTimeParser.class;
    }

    @Override
    public boolean isSingleton() {
        return IS_SINGLETON;
    }
}
