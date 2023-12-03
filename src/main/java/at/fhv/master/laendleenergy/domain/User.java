package at.fhv.master.laendleenergy.domain;

import java.time.LocalDate;
import java.util.Optional;

public class User extends Member {
    private String emailAddress;
    private String password;
    private Role role;
    private String deviceId;

    public User() {
        super();
    }

    public User(String emailAddress, String password, Role role, String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender, String deviceId) {
        super(name, dateOfBirth, gender);
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
        this.deviceId = deviceId;
    }

    public User(String emailAddress, String password, Role role, String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender) {
        super(name, dateOfBirth, gender);
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }

    public User(String id, String emailAddress, String password, Role role, String name, Optional<LocalDate> dateOfBirth, Optional<Gender> gender) {
        super(id, name, dateOfBirth, gender);
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
                ", deviceId= " + getDeviceId() +
                '}';
    }
}
