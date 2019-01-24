package pl.kkp.core.bean;

import org.springframework.beans.factory.FactoryBean;
import pl.kkp.core.util.date.DateParser;

public class DateParserFactory implements FactoryBean<DateParser> {
    public static final boolean IS_SINGLETON = true;

    private String dateFmt;

    public DateParserFactory(String dateFmt) {
        this.dateFmt = dateFmt;
    }

    @Override
    public DateParser getObject() throws Exception {
        return new DateParser(dateFmt);
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
