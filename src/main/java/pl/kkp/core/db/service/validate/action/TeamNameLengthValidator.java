package pl.kkp.core.db.service.validate.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.Team;

@Component
public class TeamNameLengthValidator extends FieldLengthValidator<Team> {

    public static final String VALIDATED_FIELD = "team.name";
    public static final int MIN_LEN_INCLUSIVE = 0;
    public static final int MAX_LEN_INCLUSIVE = 64;

    public TeamNameLengthValidator() {
        super(VALIDATED_FIELD, MIN_LEN_INCLUSIVE, MAX_LEN_INCLUSIVE);
    }

    @Override
    public int getFieldLength(Team entity) {
        String teamName = entity.getName();

        return StringUtils.length(teamName);
    }
}
