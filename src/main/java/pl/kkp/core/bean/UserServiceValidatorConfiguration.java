package pl.kkp.core.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.UserEmailUniqueValidator;
import pl.kkp.core.db.service.validate.action.UserLoginFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserLoginUniqueValidator;
import pl.kkp.core.db.service.validate.action.UserPasswordFieldSetValidator;
import pl.kkp.core.db.service.validate.action.UserPasswordLengthValidator;
import pl.kkp.core.db.service.validate.action.ValidatorAction;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class UserServiceValidatorConfiguration {

    private Map<ValidatorActionType, List<? extends ValidatorAction<User>>> actions;

    @Autowired
    public UserServiceValidatorConfiguration(UserLoginFieldSetValidator userLoginFieldSetValidator,
                                             UserPasswordFieldSetValidator userPasswordFieldSetValidator,
                                             UserLoginUniqueValidator userLoginUniqueValidator,
                                             UserEmailUniqueValidator userEmailUniqueValidator,
                                             UserPasswordLengthValidator userPasswordLengthValidator) {
        this.actions = new LinkedHashMap<ValidatorActionType, List<? extends ValidatorAction<User>>>() {
            {
                put(ValidatorActionType.SAVE,
                        Arrays.asList(
                                userLoginFieldSetValidator,
                                userPasswordFieldSetValidator,
                                userLoginUniqueValidator,
                                userEmailUniqueValidator,
                                userPasswordLengthValidator
                        )
                );
            }
        }
        ;
    }


    @Bean
    public UserServiceValidatorFactory userServiceValidatorFactory() {
        return new UserServiceValidatorFactory(actions);
    }

    @Bean
    public ServiceValidator<User> userServiceValidator(
            UserServiceValidatorFactory userServiceValidatorFactory) throws Exception {
        return userServiceValidatorFactory.getObject();
    }
}
