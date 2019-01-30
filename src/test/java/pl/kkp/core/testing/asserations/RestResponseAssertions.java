package pl.kkp.core.testing.asserations;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.BaseRsp;
import pl.kkp.core.db.service.validate.ServiceValidator;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.testing.mocks.ServiceValidatorMocks;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static pl.kkp.core.testing.mocks.EntityExistServiceValidatorMocks.buildEntityExistValidationMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.buildFieldTooLongValidationMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.buildFieldTooShortValidationMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;
import static pl.kkp.core.testing.mocks.ServiceValidatorMocks.buildEntityWithIdNotFoundValidationMessage;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValueValidationMessage;

public class RestResponseAssertions {
    public static void assertResponseStatusCode(ResponseEntity response, HttpStatus status) {
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(status);
    }

    public static void assertResponseStatusCodeOk(ResponseEntity response) {
        assertResponseStatusCode(response, HttpStatus.OK);
    }

    public static <T> void assertReturn400HttpCodeWhenEntityNotExist(
            ValidatorActionType action, ResponseEntity<T> response, String validatedField, String validatedParam) {
        String expectedMsg = buildEntityExistValidationMessage(action, validatedField, validatedParam);
        assertReturn400HttpCodeWithMessage(expectedMsg, response);
    }

    public static <T> void assertReturn400HttpCodeWhenFieldNotSet(
            ValidatorActionType action, ResponseEntity<T> response, String validatedField) {
        String expectedMsg = buildFiledNotSetValidationMessage(action, validatedField);
        assertReturn400HttpCodeWithMessage(expectedMsg, response);
    }

    public static <T> void assertReturn400HttpCodeWhenFieldNotUnique(
            ValidatorActionType action, ResponseEntity<T> response, String validatedField) {
        String expectedMessage = buildUniqueValueValidationMessage(action, validatedField);
        assertReturn400HttpCodeWithMessage(expectedMessage, response);
    }

    public static <T> void assertReturn400HttpCodeWhenFieldTooLong(
            ValidatorActionType action, ResponseEntity<T> response, String validatedField, int actualLen, int maxLen) {
        String expectedMessage = buildFieldTooLongValidationMessage(action, validatedField, maxLen, actualLen);
        assertReturn400HttpCodeWithMessage(expectedMessage, response);
    }

    public static <T> void assertReturn400HttpCodeWhenFieldTooShort(
            ValidatorActionType action, ResponseEntity<T> response, String validatedField, int actualLen, int minLen) {
        String expectedMessage = buildFieldTooShortValidationMessage(action, validatedField, actualLen, minLen);
        assertReturn400HttpCodeWithMessage(expectedMessage, response);
    }

    private static <T> void assertReturn204HttpCodeWithMessage(String message, ResponseEntity<T> response) {
        assertResponseStatusCode(response, HttpStatus.NO_CONTENT);
        assertBaseRspMessage(response, message);
    }

    private static <T> void assertReturn400HttpCodeWithMessage(String message, ResponseEntity<T> response) {
        assertResponseStatusCode(response, HttpStatus.BAD_REQUEST);
        assertBaseRspMessage(response, message);
    }

    private static <T> void assertBaseRspMessage(ResponseEntity<T> response, String expectedMsg) {
        assertThat(response).isNotNull();
        Object responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody).isInstanceOf(BaseRsp.class);
        BaseRsp baseResponseBody = (BaseRsp) responseBody;
        assertThat(baseResponseBody.getMessage()).isEqualTo(expectedMsg);
    }
}
