package pl.kkp.core.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.BaseRsp;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.UserEmailFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserEmailUniqueValidator;
import pl.kkp.core.db.service.validate.action.UserLoginFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserLoginUniqueValidator;
import pl.kkp.core.db.service.validate.action.UserPasswordFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserPasswordLengthValidator;
import pl.kkp.core.security.basic.http.BasicCredentials;
import pl.kkp.core.testing.TestRestController;
import pl.kkp.core.testing.asserations.RestResponseAssertions;
import pl.kkp.core.util.RandomStringGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.kkp.core.testing.asserations.RestResponseAssertions.assertResponseStatusCodeOk;


public class TestUserController extends TestRestController {
    private static final String ENDPOINT_PATH = "/user";

    @Autowired
    private BasicCredentials adminCredentials;

    private RandomStringGenerator passwordGenerator;

    public TestUserController() {
        super(ENDPOINT_PATH);
        passwordGenerator = new RandomStringGenerator(UserPasswordLengthValidator.MAX_PASSWORD_LENGTH + 1);
    }

    @Test
    public void isNewUserCreated() {
        Integer id = null;
        String login = "ulogin";
        String pass = "upass";
        String nick = "unick";
        String email = "user@domain.com";
        Boolean isEnabled = false;
        UserModel userToCreate = new UserModel(id, login, pass, nick, email, isEnabled);
        String endpointPath = getEndpointPath("");

        ResponseEntity<UserModel> createdUserRsp = authorizedPost(adminCredentials, endpointPath, userToCreate,
                UserModel.class);

        assertResponseStatusCodeOk(createdUserRsp);

        UserModel createdUser = createdUserRsp.getBody();

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getLogin()).isEqualTo(login);
        assertThat(createdUser.getPassword()).isEqualTo(pass);
        assertThat(createdUser.getNick()).isEqualTo(nick);
        assertThat(createdUser.getEmail()).isEqualTo(email);
        assertThat(createdUser.getEnabled()).isEqualTo(true);
    }

    @Test
    public void isGetUserByLogin() {
        String searchedLogin = "test-admin";
        String loginQueryParam = String.format("?login=%s", searchedLogin);
        String endpointPath = ENDPOINT_PATH.concat(loginQueryParam);
        endpointPath = getServerPath(endpointPath);

        ResponseEntity<UserModel> response = authorizedGet(adminCredentials, endpointPath, UserModel.class);

        UserModel foundUser = response.getBody();
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getLogin()).isEqualTo(searchedLogin);
        assertThat(foundUser.getRoles())
                .isNotEmpty()
                .flatExtracting("roles")
                .extracting("authority")
                .contains("ROLE_ADMIN", "ROLE_USER");
    }

    @Test
    public void isGetAllUsers() {
        String endpointPath = getServerPath("/users");
        ParameterizedTypeReference<ArrayList<UserModel>> rspType =
                new ParameterizedTypeReference<ArrayList<UserModel>>() {};

        ResponseEntity<ArrayList<UserModel>> response = authorizedGet(adminCredentials, endpointPath, rspType);
        assertResponseStatusCodeOk(response);

        List<UserModel> userModels = response.getBody();
        assertThat(userModels).isNotEmpty()
                .size()
                .isGreaterThanOrEqualTo(1);
        assertThat(userModels).extracting("login")
                .contains("test-admin");
    }

    @Test
    public void isReturn400HttpCodeWhenNotUniqueUserLogin() {
        UserModel userToCreate = new UserModel();
        userToCreate.setLogin("test-admin");
        userToCreate.setEmail("email@domain.pl");
        userToCreate.setPassword("secret");

        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, userToCreate, BaseRsp.class);

        String validatedField = UserLoginUniqueValidator.VALIDATED_FIELD;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldNotUnique(action, response, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenNotUniqueEmail() {
        UserModel userToCreate = new UserModel();
        userToCreate.setLogin("uniqueUser18987");
        userToCreate.setEmail("test-admin@domain.com");
        userToCreate.setPassword("secret");

        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, userToCreate, BaseRsp.class);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserEmailUniqueValidator.VALIDATED_FIELD;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldNotUnique(action, response, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenEmailNotSet() {
        UserModel userToCreate = new UserModel();
        userToCreate.setLogin("uniqueUser82013");
        userToCreate.setEmail("");
        userToCreate.setPassword("secret");

        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(
                adminCredentials, endpointPath, userToCreate, BaseRsp.class);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserEmailFieldSetValidator.VALIDATED_FIELD;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldNotSet(action, response, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenPasswordNotSet() {
        UserModel userToCreate = new UserModel();
        userToCreate.setLogin("uniqueUser12879");
        userToCreate.setEmail("user12879@domain.com");
        userToCreate.setPassword("");

        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, userToCreate, BaseRsp.class);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserPasswordFieldSetValidator.VALIDATED_FIELD;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldNotSet(action, response, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenUserLoginNotSet() {
        String login = "";
        String email = "user@domain.com";
        String password = "secret";
        UserModel userToCreate = new UserModel(login, password, email);

        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, userToCreate, BaseRsp.class);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserLoginFieldSetValidator.VALIDATED_FIELD;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldNotSet(action, response, validatedField);
    }

    @Test
    public void isReturn400HttpCodeWhenUserPasswordTooLong() {
        String login = "uniqueUser15730";
        String email = "user15730@domain.com";
        String password = passwordGenerator.generate();
        UserModel userToCreate = new UserModel(login, password, email);

        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, userToCreate, BaseRsp.class);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserPasswordLengthValidator.VALIDATED_FIELD;
        int passLen = password.length();
        int maxPassLen = UserPasswordLengthValidator.MAX_PASSWORD_LENGTH;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldTooLong(
                action, response, validatedField, passLen, maxPassLen);
    }

    @Test
    public void isReturn400HttpCodeWhenUserPasswordTooShort() {
        String login = "uniqueUser00238";
        String email = "user00238@domain.com";
        String password = "***";
        UserModel userToCreate = new UserModel(login, password, email);

        String endpointPath = getEndpointPath("");

        ResponseEntity<BaseRsp> response = authorizedPost(adminCredentials, endpointPath, userToCreate, BaseRsp.class);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String validatedField = UserPasswordLengthValidator.VALIDATED_FIELD;
        int passLen = password.length();
        int minPassLen = UserPasswordLengthValidator.MIN_PASSWORD_LENGTH;
        RestResponseAssertions.assertReturn400HttpCodeWhenFieldTooShort(
                action, response, validatedField, passLen, minPassLen);
    }
}
