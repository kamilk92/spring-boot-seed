package pl.kkp.core.db.service.validate.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.Tournament;

@Component
public class TournamentNameFieldLengthValidator extends FieldLengthValidator<Tournament> {
    public static final int MAX_NAME_LENGTH = 128;
    public static final String VALIDATED_FIELD = "tournament.name";

    public TournamentNameFieldLengthValidator() {
        super(VALIDATED_FIELD, 0, MAX_NAME_LENGTH);
    }

    @Override
    public int getFieldLength(Tournament entity) {
        String name = entity.getName();

        return StringUtils.length(name);
    }
}
