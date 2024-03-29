package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "household_user")
public class User extends Member {
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        super();
    }

    public User(String emailAddress, String password, Role role, String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender, String householdId, String deviceId) {
        super(name, dateOfBirth, gender, householdId, deviceId);
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }

    public User(String id, String emailAddress, String password, Role role, String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender, String householdId, String deviceId) {
        super(id, name, dateOfBirth, gender, householdId, deviceId);
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
}
