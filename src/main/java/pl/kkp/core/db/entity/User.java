package pl.kkp.core.db.entity;


import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String login;

    private String password;

    private String nick;

    private String email;

    private Boolean isEnabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<UserRole> roles;

    public User() {
    }

    public User(User user) {
        id = user.getId();
        login = user.getLogin();
        password = user.getPassword();
        nick = user.getNick();
        email = user.getEmail();
        isEnabled = user.getEnabled();
    }

    public User(String login) {
        this.login = login;
    }

    public User(List<UserRole> roles) {
        this.roles = roles;
    }

    public User(Integer id, String login, String password, String nick, String email, Boolean isEnabled) {
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

    public Collection<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
