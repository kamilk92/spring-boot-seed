package pl.kkp.core.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.TournamentModel;
import pl.kkp.core.controller.model.TournamentSeasonModel;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentSeasonBeginDateFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentSeasonTournamentExistValidator;
import pl.kkp.core.db.service.validate.action.TournamentSeasonTournamentIdFieldSetValidator;
import pl.kkp.core.db.service.validate.action.ValidatorAction;
import pl.kkp.core.testing.TestRestController;
import pl.kkp.core.util.date.DateParser;
import pl.kkp.core.util.date.DateToLocalDateTimeConverter;
import pl.kkp.core.util.date.LocalDateTimeParser;

import javax.transaction.NotSupportedException;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

public class TestTournamentSeasonController extends TestRestController {

    private static final String ENDPOINT_PATH = "/tournament";
    private static final int EXISTING_TOURNAMENT_ID = 0;

    private TournamentSeasonModel season;

    public TestTournamentSeasonController() {
        super(ENDPOINT_PATH);
    }

    @Autowired
    private LocalDateTimeParser localDateTimeParser;

    @Autowired
    private DateToLocalDateTimeConverter dateToLocalDateTimeConverter;

    @Before
    public void setUp() throws ParseException {
        season = setUpSeason();
    }

    @Test
    public void isCreateNewSeason() {
        String endpointPath = getSeasonEndpointPath(EXISTING_TOURNAMENT_ID);
        ResponseEntity<TournamentSeasonModel> tournamentSeason = restTemplate.postForEntity(
                endpointPath, season, TournamentSeasonModel.class);

        assertResponseStatusCodeOk(tournamentSeason);
    }

    @Test
    public void isReturn400HttpCodeWhenSeasonBeginDateNotSet() {
        String endpointPath = getSeasonEndpointPath(EXISTING_TOURNAMENT_ID);
        season.setBeginDate(null);
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentSeasonBeginDateFieldSetValidator.VALIDATED_FIELD;

        assertReturn400HttpCodeWhenFieldNotSet(season, action, endpointPath, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenTournamentWithGiveIdNotExist() {
        Integer tournamentId = 1024;
        String endpointPath = getSeasonEndpointPath(tournamentId);
        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentSeasonTournamentExistValidator.VALIDATED_FIELD;
        String validatedParam = TournamentSeasonTournamentExistValidator.VALIDATED_PARAMETER;

        assertReturn400HttpCodeWhenEntityNotExist(season, action, endpointPath, validatedField, validatedParam);
    }

    private String getSeasonEndpointPath(int tournamentId) {
        String path = String.format("/%d/season", tournamentId);

        return getEndpointPath(path);
    }

    private TournamentSeasonModel setUpSeason() throws ParseException {
        LocalDateTime beginDate = localDateTimeParser.parse("24-01-2019 15:01:00");
        final boolean isOpen = false;
        TournamentModel tournament = new TournamentModel(EXISTING_TOURNAMENT_ID);

        return new TournamentSeasonModel(beginDate, isOpen, tournament);
    }
}
