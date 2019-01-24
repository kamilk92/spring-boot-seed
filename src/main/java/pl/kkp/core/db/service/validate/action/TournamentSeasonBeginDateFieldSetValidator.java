package pl.kkp.core.db.service.validate.action;

import org.springframework.stereotype.Component;
import pl.kkp.core.db.entity.TournamentSeason;

@Component
public class TournamentSeasonBeginDateFieldSetValidator extends FieldSetValidator<TournamentSeason> {
    public static final String VALIDATED_FIELD = "tournamentSeason.beginDate";

    public TournamentSeasonBeginDateFieldSetValidator() {
        super(VALIDATED_FIELD);
    }

    @Override
    public boolean isFieldSet(TournamentSeason entity) {
        return entity.getBeginDate() != null;
    }
}
