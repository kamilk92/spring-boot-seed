package pl.kkp.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.kkp.core.controller.model.BaseRsp;
import pl.kkp.core.db.service.validate.exception.ValidationException;

@ControllerAdvice
@RestController
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BaseRsp> handleValidationException(ValidationException exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        BaseRsp baseRsp = new BaseRsp(exceptionMessage);

        return new ResponseEntity<>(baseRsp, HttpStatus.BAD_REQUEST);
    }

}
