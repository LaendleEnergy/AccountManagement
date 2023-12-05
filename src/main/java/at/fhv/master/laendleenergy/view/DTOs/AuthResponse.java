package at.fhv.master.laendleenergy.view.DTOs;

public class AuthResponse {

    public String token;
    public String userId;

    public AuthResponse() {

    }

    public AuthResponse(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}