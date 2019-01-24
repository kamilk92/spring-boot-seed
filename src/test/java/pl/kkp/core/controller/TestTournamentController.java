package pl.kkp.core.controller;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.TournamentModel;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentNameFieldLengthValidator;
import pl.kkp.core.db.service.validate.action.TournamentNameFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TournamentNameUniqueValidator;
import pl.kkp.core.testing.TestRestController;
import pl.kkp.core.util.RandomStringGenerator;
import pl.kkp.core.util.StringGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestTournamentController extends TestRestController {

    private static final String ENDPOINT_PATH = "/tournament";

    private StringGenerator tournamentNameGenerator;

    public TestTournamentController() {
        super(ENDPOINT_PATH);
        tournamentNameGenerator =
                new RandomStringGenerator(TournamentNameFieldLengthValidator.MAX_NAME_LENGTH + 1);
    }

    @Test
    public void isCreateNewTournament() {
        String endpointPath = getEndpointPath("");

        Integer id = null;
        String name = "Championship";
        String description = "England second league";
        TournamentModel tournament = new TournamentModel(id, name, description);

        ResponseEntity<TournamentModel> createdTournamentRsp = restTemplate.postForEntity(
                endpointPath, tournament, TournamentModel.class);

        assertResponseStatusCodeOk(createdTournamentRsp);

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
        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");
        String validatedField = TournamentNameFieldSetValidator.VALIDATED_FIELD;

        assertReturn400HttpCodeWhenFieldNotSet(tournament, action, endpointPath, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenTournamentNameNotUnique() {
        String tournamentName = "Test tournament";
        Tournament tournament = new Tournament(tournamentName);
        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");
        String validatedField = TournamentNameUniqueValidator.VALIDATED_FIELD;

        assertReturn400HttpCodeWhenFieldNotUnique(tournament, action, endpointPath, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenTournamentNameIsTooLong() {
        String tournamentName = tournamentNameGenerator.generate();
        Tournament tournament = new Tournament(tournamentName);
        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");
        String validatedField = TournamentNameFieldLengthValidator.VALIDATED_FIELD;
        int maxNameLength = TournamentNameFieldLengthValidator.MAX_NAME_LENGTH;
        int nameLength = tournamentName.length();

        assertReturn400HttpCodeWhenFieldTooLong(
                tournament, action, endpointPath, validatedField, nameLength, maxNameLength);
    }
}
