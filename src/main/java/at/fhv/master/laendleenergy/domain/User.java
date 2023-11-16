package at.fhv.master.laendleenergy.domain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class User extends Member {
    private final String userId;
    private String emailAddress;
    private String password;
    private Role role;

    public User() {
        super();
        this.userId = UUID.randomUUID().toString();
    }

    public User(String emailAddress, String password, Role role, String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender) {
        super(name, dateOfBirth, gender);
        this.userId = UUID.randomUUID().toString();
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }
}
