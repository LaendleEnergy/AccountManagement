package at.fhv.master.laendleenergy.view.DTOs;

public class LoginDTO {
    private String token;
    private String deviceId;

    public LoginDTO() {

    }

    public LoginDTO(String token, String deviceId) {
        this.token = token;
        this.deviceId = deviceId;
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
}
