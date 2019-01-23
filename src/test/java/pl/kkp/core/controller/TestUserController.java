package pl.kkp.core.controller;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.UserEmailFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserEmailUniqueValidator;
import pl.kkp.core.db.service.validate.action.UserLoginFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserLoginUniqueValidator;
import pl.kkp.core.db.service.validate.action.UserPasswordFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserPasswordLengthValidator;
import pl.kkp.core.testing.TestRestController;
import pl.kkp.core.util.RandomStringGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TestUserController extends TestRestController {
    private static final String ENDPOINT_PATH = "/user";

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

        ResponseEntity<UserModel> createdUserRsp = restTemplate.postForEntity(
                endpointPath, userToCreate, UserModel.class);

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
        String endpointPath = String.format("login/%s", searchedLogin);
        endpointPath = getEndpointPath(endpointPath);

        UserModel foundUser = restTemplate.getForObject(endpointPath, UserModel.class);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getLogin()).isEqualTo(searchedLogin);
    }

    @Test
    public void isReturn400HttpCodeWhenNotUniqueUserLogin() {
        UserModel userToCreate = new UserModel();
        userToCreate.setLogin("test-admin");
        userToCreate.setEmail("email@domain.pl");
        userToCreate.setPassword("secret");

        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");
        String fieldName = UserLoginUniqueValidator.VALIDATED_FIELD;

        assertReturn400HttpCodeWhenFieldNotUnique(userToCreate, action, endpointPath, fieldName);
    }

    @Test
    public void isReturn400HttpCodeWhenNotUniqueEmail() {
        UserModel userToCreate = new UserModel();
        userToCreate.setLogin("uniqueUser18987");
        userToCreate.setEmail("test-admin@domain.com");
        userToCreate.setPassword("secret");

        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");
        String fieldName = UserEmailUniqueValidator.VALIDATED_FIELD;

        assertReturn400HttpCodeWhenFieldNotUnique(userToCreate, action, endpointPath, fieldName);
    }

    @Test
    public void isReturn400HttpCodeWhenEmailNotSet() {
        UserModel userToCreate = new UserModel();
        userToCreate.setLogin("uniqueUser82013");
        userToCreate.setEmail("");
        userToCreate.setPassword("secret");

        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");
        String validatedFieldName = UserEmailFieldSetValidator.VALIDATED_FIELD;

        assertReturn400HttpCodeWhenFieldNotSet(userToCreate, action, endpointPath, validatedFieldName);
    }

    @Test
    public void isReturn400HttpCodeWhenPasswordNotSet() {
        UserModel userToCreate = new UserModel();
        userToCreate.setLogin("uniqueUser12879");
        userToCreate.setEmail("user12879@domain.com");
        userToCreate.setPassword("");

        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");
        String validatedFieldName = UserPasswordFieldSetValidator.VALIDATED_FIELD;

        assertReturn400HttpCodeWhenFieldNotSet(userToCreate, action, endpointPath, validatedFieldName);
    }

    @Test
    public void isReturn400HttpCodeWhenUserLoginNotSet() {
        String login = "";
        String email = "user@domain.com";
        String password = "secret";
        UserModel userToCreate = new UserModel(login, password, email);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");
        String validatedFieldName = UserLoginFieldSetValidator.VALIDATED_FIELD;

        assertReturn400HttpCodeWhenFieldNotSet(userToCreate, action, endpointPath, validatedFieldName);
    }

    @Test
    public void isReturn400HttpCodeWhenUserPasswordTooLong() {
        String login = "uniqueUser15730";
        String email = "user15730@domain.com";
        String password = passwordGenerator.generate();
        UserModel userToCreate = new UserModel(login, password, email);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");
        String validatedFieldName = UserPasswordLengthValidator.VALIDATED_FIELD;
        int maxPassLen = UserPasswordLengthValidator.MAX_PASSWORD_LENGTH;
        int passLen = password.length();

        assertReturn400HttpCodeWhenFieldTooLong(
                userToCreate, action, endpointPath, validatedFieldName, passLen, maxPassLen);
    }

    @Test
    public void isReturn400HttpCodeWhenUserPasswordTooShort() {
        String login = "uniqueUser00238";
        String email = "user00238@domain.com";
        String password = "***";
        UserModel userToCreate = new UserModel(login, password, email);

        ValidatorActionType action = ValidatorActionType.SAVE;
        String endpointPath = getEndpointPath("");
        String validatedFieldName = UserPasswordLengthValidator.VALIDATED_FIELD;
        int minPassLen = UserPasswordLengthValidator.MIN_PASSWORD_LENGTH;
        int passLen = password.length();

        assertReturn400HttpCodeWhenFieldTooShort(
                userToCreate, action, endpointPath, validatedFieldName, passLen, minPassLen);
    }
}
