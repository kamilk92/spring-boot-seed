package pl.kkp.core.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.BaseRsp;
import pl.kkp.core.controller.model.TournamentModel;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentNameFieldLengthValidator;
import pl.kkp.core.db.service.validate.action.TournamentNameFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentNameUniqueValidator;
import pl.kkp.core.security.basic.http.BasicCredentials;
import pl.kkp.core.testing.TestRestController;
import pl.kkp.core.testing.asserations.RestResponseAssertions;
import pl.kkp.core.util.RandomStringGenerator;
import pl.kkp.core.util.StringGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestTournamentController extends TestRestController {

    private static final String ENDPOINT_PATH = "/tournament";

    @Autowired
    private BasicCredentials adminCredentials;

    private StringGenerator tournamentNameGenerator;

    public TestTournamentController() {
        super(ENDPOINT_PATH);
        tournamentNameGenerator =
                new RandomStringGenerator(TournamentNameFieldLengthValidator.MAX_NAME_LENGTH + 1);
    }

    @Test
    public void isCreateNewTournament() {
        Integer id = null;
        String name = "Championship";
        String description = "England second league";
        TournamentModel tournament = new TournamentModel(id, name, description);
        String endpointPath = getEndpointPath("");

        ResponseEntity<TournamentModel> createdTournamentRsp = authorizedPost(
                adminCredentials, endpointPath, tournament, TournamentModel.class);

        RestResponseAssertions.assertResponseStatusCodeOk(createdTournamentRsp);

        TournamentModel createdTournament = createdTournamentRsp.getBody();
        assertThat(createdTournament).isNotNull();
        assertThat(createdTournament.getId()).isNotNull();
        assertThat(createdTournament.getName()).isEqualTo(name);
        assertThat(createdTournament.getDescription()).isEqualTo(description);
    }

    @Test
    public void isReturn400HttpCodeWhenTournamentNameNotSet() {
        String tournamentName = "";
        String tournamentDescription = "Tournament12487 description.";
        Tournament tournament = new Tournament(tournamentName, tournamentDescription);
        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(
                adminCredentials, endpointPath, tournament, BaseRsp.class);

        String validatedField = TournamentNameFieldSetValidator.VALIDATED_FIELD;
        ValidatorActionType action = ValidatorActionType.SAVE;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldNotSet(action, response, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenTournamentNameNotUnique() {
        String tournamentName = "Test tournament";
        Tournament tournament = new Tournament(tournamentName);
        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = restTemplate.authorizedPost(
                endpointPath, tournament, BaseRsp.class, adminCredentials);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TournamentNameUniqueValidator.VALIDATED_FIELD;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldNotUnique(action, response, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenTournamentNameIsTooLong() {
        String tournamentName = tournamentNameGenerator.generate();
        Tournament tournament = new Tournament(tournamentName);
        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, tournament, BaseRsp.class);

        String validatedField = TournamentNameFieldLengthValidator.VALIDATED_FIELD;
        ValidatorActionType action = ValidatorActionType.SAVE;
        int nameLength = tournamentName.length();
        int maxNameLength = TournamentNameFieldLengthValidator.MAX_NAME_LENGTH;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldTooLong(
                action, response, validatedField, nameLength, maxNameLength);
    }
}
