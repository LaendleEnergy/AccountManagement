package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Role;
import at.fhv.master.laendleenergy.domain.User;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class UpdateUserDTO {

    private String emailAddress;
    private String password;
    private String name;
    private String dateOfBirth;
    private String gender;

    public UpdateUserDTO() {

    }

    public UpdateUserDTO(String emailAddress, String password, String name, String dateOfBirth, String gender) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public static User create(UpdateUserDTO userDTO, String role) {
        System.out.println(userDTO.toString());
        return new User(
                userDTO.getEmailAddress(),
                userDTO.getPassword(),
                Role.get(role),
                userDTO.getName(),
                !Objects.equals(userDTO.getGender(), "") ? Optional.of(LocalDate.parse(userDTO.getDateOfBirth())) : Optional.empty(),
                !Objects.equals(userDTO.getGender(), "") ? Optional.of(Gender.get(userDTO.getGender())) : Optional.empty()
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
