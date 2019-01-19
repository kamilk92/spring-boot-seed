package pl.kkp.core.controller;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.testing.TestRestController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TestUserController extends TestRestController {
    private static final String ENDPOINT_PATH = "/user";

    public TestUserController() {
        super(ENDPOINT_PATH);
    }

    @Test
    public void isNewUserCreated() {
        Integer id = null;
        String login = "ulogin";
        String pass = "upass";
        String nick = "unick";
        String email = "user@domain.com";
        Boolean isEnabled = false;
        String endpointPath = getEndpointPath("");

        UserModel userToCreate = new UserModel(id, login, pass, nick, email, isEnabled);

        ResponseEntity<UserModel> createdUserRsp = restTemplate.postForEntity(
                endpointPath, userToCreate, UserModel.class);

        assertResponseStatusCode(createdUserRsp);

        UserModel createdUser = createdUserRsp.getBody();

        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getLogin()).isEqualTo(login);
        assertThat(createdUser.getPassword()).isEqualTo(pass);
        assertThat(createdUser.getNick()).isEqualTo(nick);
        assertThat(createdUser.getEmail()).isEqualTo(email);
        assertThat(createdUser.getEnabled()).isEqualTo(isEnabled);
    }

    @Test
    public void isGetUserByLogin() {
        String searchedLogin = "test-admin";
        String endpointPath = String.format("login/%s", searchedLogin);
        endpointPath = getEndpointPath(endpointPath);

        UserModel foundUser = restTemplate.getForObject(endpointPath, UserModel.class);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getLogin()).isEqualTo(searchedLogin);
    }
}
