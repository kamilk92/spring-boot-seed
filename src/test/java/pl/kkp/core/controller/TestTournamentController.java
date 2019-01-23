package pl.kkp.core.controller;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.TournamentModel;
import pl.kkp.core.testing.TestRestController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestTournamentController extends TestRestController {

    private static final String ENDPOINT_PATH = "/tournament";

    public TestTournamentController() {
        super(ENDPOINT_PATH);
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

}
