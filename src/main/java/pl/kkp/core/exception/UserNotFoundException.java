package pl.kkp.core.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends UsernameNotFoundException {

    public static final String USER_NOT_FOUND_EXCEPTION = "User with getUserByLogin '%s' not found.";

    public UserNotFoundException(String userLogin) {
        super(String.format(USER_NOT_FOUND_EXCEPTION, userLogin));
    }
}
