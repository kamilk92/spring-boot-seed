package pl.kkp.core.db.service.exception;

public class EntityWithIdNotFoundException extends EntityNotFoundException {

    public static final String EXCEPTION_MESSAGE_FMT = "Entity with id '%s' not found";

    public EntityWithIdNotFoundException(Number id) {
        super(String.format(EXCEPTION_MESSAGE_FMT, String.valueOf(id)));
    }
}
