package at.fhv.master.laendleenergy.view.DTOs;

public class LoginDTO {
    public String token;
    public String deviceId;
    public String userId;

    public LoginDTO() {

    }

    public LoginDTO(String token, String deviceId, String userId) {
        this.token = token;
        this.deviceId = deviceId;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
