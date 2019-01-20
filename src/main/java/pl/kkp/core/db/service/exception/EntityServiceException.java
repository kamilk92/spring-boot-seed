package pl.kkp.core.db.service.exception;

import pl.kkp.core.exception.ApplicationException;

public class EntityServiceException extends ApplicationException {
    public EntityServiceException(String message) {
        super(message);
    }
}
