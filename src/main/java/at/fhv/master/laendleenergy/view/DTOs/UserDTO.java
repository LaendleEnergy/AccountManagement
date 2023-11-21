package at.fhv.master.laendleenergy.view.DTOs;

public class UserDTO {
    private String emailAddress;
    private String password;
    private String role;
    private String name;
    private String dateOfBirth;
    private String gender;

    public UserDTO(String emailAddress, String password, String role, String name, String dateOfBirth, String gender) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
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
