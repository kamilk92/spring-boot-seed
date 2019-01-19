package pl.kkp.core.controller;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.testing.TestRestController;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestUserController extends TestRestController {

    @Test
    public void isNewUserCreated() {
        String endpointPath = "/user";
        endpointPath = getEndpointAddress(endpointPath);

        Integer id = null;
        String login = "ulogin";
        String pass = "upass";
        String nick = "unick";
        String email = "user@domain.com";
        Boolean isEnabled = false;

        UserModel userToCreate = new UserModel(id, login, pass, nick, email, isEnabled);

        ResponseEntity<UserModel> createdUserRsp = restTemplate.postForEntity(endpointPath, userToCreate, UserModel.class);

        assertResponseStatusCode(createdUserRsp);

        UserModel createdUser = createdUserRsp.getBody();

        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getLogin()).isEqualTo(login);
        assertThat(createdUser.getPassword()).isEqualTo(pass);
        assertThat(createdUser.getNick()).isEqualTo(nick);
        assertThat(createdUser.getEmail()).isEqualTo(email);
        assertThat(createdUser.getEnabled()).isEqualTo(isEnabled);
    }
}
