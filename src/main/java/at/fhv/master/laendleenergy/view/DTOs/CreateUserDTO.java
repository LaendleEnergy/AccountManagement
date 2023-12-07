package at.fhv.master.laendleenergy.view.DTOs;

public class CreateUserDTO {
    private String emailAddress;
    private String name;
    private String password;

    public CreateUserDTO() {

    }

    public CreateUserDTO(String emailAddress, String name, String password) {
        this.emailAddress = emailAddress;
        this.name = name;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
