package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
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

    public User(String emailAddress, String password, Role role, String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender, Household household) {
        super(name, dateOfBirth, gender, household);
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }

    public User(String id, String emailAddress, String password, Role role, String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender, Household household) {
        super(id, name, dateOfBirth, gender, household);
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + getId() + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", name=" + getName() +
                ", gender= " + getGender() +
                ", dateOfBirth= " + getDateOfBirth() +
                ", deviceId= " + getHousehold().getDeviceId() +
                ", householdId= " + getHousehold().getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(emailAddress, user.emailAddress) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), emailAddress, password, role);
    }
}
