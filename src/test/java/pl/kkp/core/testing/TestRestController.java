package pl.kkp.core.testing;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.kkp.core.controller.model.BaseRsp;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.FieldLengthValidator;
import pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks;
import pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.buildFieldTooLongValidationMessage;
import static pl.kkp.core.testing.mocks.FieldLengthServiceValidatorMocks.buildFieldTooShortValidationMessage;
import static pl.kkp.core.testing.mocks.FieldSetServiceValidatorMocks.buildFiledNotSetValidationMessage;
import static pl.kkp.core.testing.mocks.UniqueValueServiceValidatorMocks.buildUniqueValueValidationMessage;

public abstract class TestRestController extends SpringBootBaseTest {
    private static final String APP_ADDRESS = "http://localhost";

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String endpointBasePathFormat;

    protected TestRestController(String endpointBasePath) {
        endpointBasePathFormat = buildEndpointBasePathFormat(endpointBasePath);
    }

    protected void assertBaseRspMessage(ResponseEntity<BaseRsp> response, String expectedMsg) {
        assertThat(response).isNotNull();
        BaseRsp responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMessage()).isEqualTo(expectedMsg);
    }

    protected void assertResponseStatusCode(ResponseEntity response, HttpStatus status) {
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(status);
    }

    protected void assertResponseStatusCodeOk(ResponseEntity response) {
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    protected void assertReturn400HttpCodeWhenFieldNotSet(
            Object entity, ValidatorActionType action, String endpointPath, String fieldName) {
        String expectedMsg = buildFiledNotSetValidationMessage(action, fieldName);
        assertReturn400HttpCodeWithMessage(entity, endpointPath, expectedMsg);
    }

    protected void assertReturn400HttpCodeWhenFieldNotUnique(
            Object entity, ValidatorActionType action, String endpointPath, String fieldName) {
        String expectedMessage = buildUniqueValueValidationMessage(action, fieldName);
        assertReturn400HttpCodeWithMessage(entity, endpointPath, expectedMessage);
    }

    protected void assertReturn400HttpCodeWhenFieldTooLong(
            FieldLengthValidator validator,
            Object entity,
            ValidatorActionType action,
            String endpointPath,
            int actualPassLen
    ) {
        String expectedMessage = buildFieldTooLongValidationMessage(action, validator, actualPassLen);
        assertReturn400HttpCodeWithMessage(entity, endpointPath, expectedMessage);
    }

    protected void assertReturn400HttpCodeWhenFieldTooLong(
            Object entity,
            ValidatorActionType action,
            String endpointPath,
            String validatedField,
            int actualLen,
            int maxLen
    ) {
        String expectedMessage = buildFieldTooLongValidationMessage(action, validatedField, maxLen, actualLen);
        assertReturn400HttpCodeWithMessage(entity, endpointPath, expectedMessage);
    }

    protected void assertReturn400HttpCodeWhenFieldTooShort(
            Object entity,
            ValidatorActionType action,
            String endpointPath,
            String fieldName,
            int actualPassLen,
            int minPassLen
    ) {
        String expectedMessage = buildFieldTooShortValidationMessage(action, fieldName, actualPassLen, minPassLen);
        assertReturn400HttpCodeWithMessage(entity, endpointPath, expectedMessage);
    }

    protected void assertReturn400HttpCodeWithMessage(
            Object entity, String endpointPath, String message) {
        ResponseEntity<BaseRsp> response = restTemplate.postForEntity(endpointPath, entity, BaseRsp.class);

        assertResponseStatusCode(response, HttpStatus.BAD_REQUEST);
        assertBaseRspMessage(response, message);
    }

    protected String getEndpointPath(String path) {
        String endpointBasePath = getEndpointBasePath(this.portNumber);
        path = buildPath(path);

        return new StringBuilder(endpointBasePath)
                .append(path)
                .toString();
    }

    protected String getEndpointBasePath(Integer port) {
        return String.format(this.endpointBasePathFormat, port);
    }

    private String buildEndpointBasePathFormat(String basePath) {
        basePath = buildPath(basePath);
        String portNumDelimiter = ":";
        String portNumFormat = "%d";

        return new StringBuilder(APP_ADDRESS)
                .append(portNumDelimiter)
                .append(portNumFormat)
                .append(basePath)
                .toString();
    }

    private String buildPath(String path) {
        if (path.startsWith("/")) {
            return path;
        }
        if (StringUtils.isNotEmpty(path)) {
            return "/" + path;
        }

        return "";
    }
}
