package pl.kkp.core.controller;

import org.apache.coyote.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.BaseRsp;
import pl.kkp.core.controller.model.TournamentModel;
import pl.kkp.core.controller.model.TournamentSeasonModel;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentSeasonBeginDateFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentSeasonTournamentExistValidator;
import pl.kkp.core.security.basic.http.BasicCredentials;
import pl.kkp.core.testing.TestRestController;
import pl.kkp.core.testing.asserations.RestResponseAssertions;
import pl.kkp.core.util.date.DateToLocalDateTimeConverter;
import pl.kkp.core.util.date.LocalDateTimeParser;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.kkp.core.testing.asserations.RestResponseAssertions.assertResponseStatusCodeOk;

public class TestTournamentSeasonController extends TestRestController {

    private static final String ENDPOINT_PATH = "/tournament";
    private static final int EXISTING_TOURNAMENT_ID = 0;

    private TournamentSeasonModel season;

    public TestTournamentSeasonController() {
        super(ENDPOINT_PATH);
    }

    @Autowired
    private BasicCredentials adminCredentials;

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

        ResponseEntity<TournamentSeasonModel> tournamentSeason = authorizedPost(adminCredentials, endpointPath,
                season, TournamentSeasonModel.class);

        assertResponseStatusCodeOk(tournamentSeason);
    }

    @Test
    public void isReturn400HttpCodeWhenSeasonBeginDateNotSet() {
        season.setBeginDate(null);
        String endpointPath = getSeasonEndpointPath(EXISTING_TOURNAMENT_ID);

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, season, BaseRsp.class);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentSeasonBeginDateFieldSetValidator.VALIDATED_FIELD;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldNotSet(action, response, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenTournamentWithGiveIdNotExist() {
        Integer tournamentId = 1024;
        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getSeasonEndpointPath(tournamentId);

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, season, BaseRsp.class);

        String validatedField = TournamentSeasonTournamentExistValidator.VALIDATED_FIELD;
        String validatedParam = TournamentSeasonTournamentExistValidator.VALIDATED_PARAMETER;
        RestResponseAssertions.assertReturn400HttpCodeWhenEntityNotExist(
                action, response, validatedField, validatedParam);
    }

    @Test
    public void isReturnAllTournamentSeasons() {
        Integer tournamentId = 0;
        String endpointPath = String.format("/%d/seasons", tournamentId);
        endpointPath = getEndpointPath(endpointPath);
        ParameterizedTypeReference<List<TournamentSeasonModel>> rspType =
                new ParameterizedTypeReference<List<TournamentSeasonModel>>() {};

        ResponseEntity<List<TournamentSeasonModel>> response =
                authorizedGet(adminCredentials, endpointPath, rspType);

        assertResponseStatusCodeOk(response);
        List<TournamentSeasonModel> responseBody = response.getBody();
        assertThat(responseBody)
                .extracting("id")
                .contains(0, 1);
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
