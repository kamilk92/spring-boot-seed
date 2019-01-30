package pl.kkp.core.db.service.exception;

public class EntityNotFoundException extends EntityServiceException {
    public static final String EXCEPTION_MESSAGE = "Entity not found.";

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
