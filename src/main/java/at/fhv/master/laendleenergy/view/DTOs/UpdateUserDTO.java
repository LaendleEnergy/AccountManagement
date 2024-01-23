package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Household;
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

    public User toUser(String userId, Role role, Household household) {
        return new User(
                userId,
                this.getEmailAddress(),
                this.getPassword(),
                role,
                this.getName(),
                !Objects.equals(this.getDateOfBirth(), "") ? Optional.of(LocalDate.parse(this.getDateOfBirth())) : Optional.empty(),
                !Objects.equals(this.getGender(), "") ? Optional.of(Gender.get(this.getGender())) : Optional.of(Gender.NONE),
                household.getId(),
                household.getDeviceId()
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
