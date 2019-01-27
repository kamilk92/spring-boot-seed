package pl.kkp.core.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.BaseRsp;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TeamNameFieldSetValidator;
import pl.kkp.core.db.service.validate.action.TeamNameLengthValidator;
import pl.kkp.core.db.service.validate.action.TeamNameUniqueValidator;
import pl.kkp.core.security.basic.http.BasicCredentials;
import pl.kkp.core.testing.TestRestController;
import pl.kkp.core.util.RandomStringGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static pl.kkp.core.testing.asserations.RestResponseAssertions.assertResponseStatusCodeOk;
import static pl.kkp.core.testing.asserations.RestResponseAssertions.assertReturn400HttpCodeWhenFieldNotSet;
import static pl.kkp.core.testing.asserations.RestResponseAssertions.assertReturn400HttpCodeWhenFieldNotUnique;
import static pl.kkp.core.testing.asserations.RestResponseAssertions.assertReturn400HttpCodeWhenFieldTooLong;

public class TestTeamController extends TestRestController {

    private static final String ENDPOINT_PATH = "/team";
    private static final String EXISTING_TEAM_NAME = "Test team";

    private Team team;

    private RandomStringGenerator teamNameGenerator;

    @Autowired
    private BasicCredentials adminCredentials;

    public TestTeamController() {
        super(ENDPOINT_PATH);
        teamNameGenerator = new RandomStringGenerator(TeamNameLengthValidator.MAX_LEN_INCLUSIVE + 1);
    }

    @Before
    public void setUp() {
        team = setUpTeam();
    }

    @Test
    public void isCreateNewTeam() {
        String endpointPath = getEndpointPath("");

        ResponseEntity<Team> response = authorizedPost(adminCredentials, endpointPath, team, Team.class);

        assertResponseStatusCodeOk(response);
        Team createdTeam = response.getBody();
        assertThat(createdTeam).isNotNull();
        assertThat(createdTeam.getId()).isNotNull();
    }

    @Test
    public void isRaiseExceptionWhenTeamNameNotSet() {
        team.setName("");
        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, team, BaseRsp.class);

        String validatedField = TeamNameFieldSetValidator.VALIDATED_FIELD;
        ValidatorActionType action = ValidatorActionType.SAVE;
        assertReturn400HttpCodeWhenFieldNotSet(action, response, validatedField);
    }

    @Test
    public void isRaiseExceptionWhenTeamNameNotUnique() {
        team.setName(EXISTING_TEAM_NAME);
        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, team, BaseRsp.class);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = TeamNameUniqueValidator.VALIDATED_FIELD;
        assertReturn400HttpCodeWhenFieldNotUnique(action, response, validatedField);
    }

    @Test
    public void isRaiseExceptionWhenTeamNameTooLong() {
        String teamName = teamNameGenerator.generate();
        team.setName(teamName);
        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, team, BaseRsp.class);

        String validatedField = TeamNameLengthValidator.VALIDATED_FIELD;
        ValidatorActionType action = ValidatorActionType.SAVE;
        final int teamNameLen = teamName.length();
        final int teamNameMaxLen = TeamNameLengthValidator.MAX_LEN_INCLUSIVE;
        assertReturn400HttpCodeWhenFieldTooLong(action, response, validatedField, teamNameLen, teamNameMaxLen);
    }

    private Team setUpTeam() {
        String teamName = "Team -431";

        return new Team(teamName);
    }

}
