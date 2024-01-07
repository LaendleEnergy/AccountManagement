package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.User;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class UserDTO {
    private String emailAddress;
    private String password;
    private String role;
    private String name;
    private String dateOfBirth;
    private String gender;

    public UserDTO() {

    }

    public UserDTO(String emailAddress, String password, String role, String name, String dateOfBirth, String gender) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public static User create(UserDTO userDTO, Household household) {
        return new User(
                userDTO.getEmailAddress(),
                userDTO.getPassword(),
                Role.get(userDTO.getRole()),
                userDTO.getName(),
                !Objects.equals(userDTO.getDateOfBirth(), "") ? Optional.of(LocalDate.parse(userDTO.getDateOfBirth())) : Optional.empty(),
                !Objects.equals(userDTO.getGender(), "") ? Optional.of(Gender.get(userDTO.getGender())) : Optional.empty(),
                household
        );
    }

    public static UserDTO create(User user) {
        return new UserDTO(
                user.getEmailAddress(),
                user.getPassword(),
                user.getRole().getName(),
                user.getName(),
                !Objects.equals(user.getDateOfBirth(), null) ? user.getDateOfBirth().toString() : "",
                !Objects.equals(user.getGender(), null) ? user.getGender().getName() : ""
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
