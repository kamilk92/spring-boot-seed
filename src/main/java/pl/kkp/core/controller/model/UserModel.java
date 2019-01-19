package pl.kkp.core.controller.model;

public class UserModel {
    private Integer id;
    private String login;
    private String password;
    private String nick;
    private String email;
    private Boolean isEnabled;

    public UserModel() {
    }

    public UserModel(Integer id, String login, String password, String nick, String email, Boolean isEnabled) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.nick = nick;
        this.email = email;
        this.isEnabled = isEnabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
