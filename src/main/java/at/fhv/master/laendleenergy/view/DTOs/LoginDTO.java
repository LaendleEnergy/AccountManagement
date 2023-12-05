package at.fhv.master.laendleenergy.view.DTOs;

public class LoginDTO {
    public String token;
    public String householdId;
    public String userId;

    public LoginDTO() {

    }

    public LoginDTO(String token, String householdId, String userId) {
        this.token = token;
        this.householdId = householdId;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
