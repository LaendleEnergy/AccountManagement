package at.fhv.master.laendleenergy.domain;

import at.fhv.master.laendleenergy.view.DTOs.UserDTO;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class User extends Member {
    private String emailAddress;
    private String password;
    private Role role;

    public User() {
        super();
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

    public static User create(UserDTO userDTO) {
        return new User(
                userDTO.getEmailAddress(),
                userDTO.getPassword(),
                Role.get(userDTO.getRole()),
                userDTO.getName(),
                Optional.ofNullable(userDTO.getDateOfBirth()).isPresent() ? Optional.of(LocalDate.parse(userDTO.getDateOfBirth())) : Optional.empty(),
                Optional.ofNullable(userDTO.getGender()).isPresent() ? Optional.of(Gender.get(userDTO.getGender())) : Optional.empty()

        );
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
                '}';
    }
}
