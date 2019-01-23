package pl.kkp.core.controller.model;

public class BaseRsp {
    private String message;

    public BaseRsp() {
    }

    public BaseRsp(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
