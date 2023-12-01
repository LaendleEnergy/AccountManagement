package at.fhv.master.laendleenergy.view.DTOs;

public class CreateHouseholdDTO {

    private String emailAddress;
    private String password;
    private String name;
    private String pricingPlan;
    private String deviceId;

    public CreateHouseholdDTO() {}

    public CreateHouseholdDTO(String emailAddress, String password, String name, String pricingPlan, String deviceId) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.name = name;
        this.pricingPlan = pricingPlan;
        this.deviceId = deviceId;
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

    public String getPricingPlan() {
        return pricingPlan;
    }

    public void setPricingPlan(String pricingPlan) {
        this.pricingPlan = pricingPlan;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
