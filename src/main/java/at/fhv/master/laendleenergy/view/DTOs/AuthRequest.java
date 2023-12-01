package at.fhv.master.laendleenergy.view.DTOs;

public class AuthRequest {

    private String emailAddress;
    private String password;

    public AuthRequest() {

    }

    public AuthRequest(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
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
}