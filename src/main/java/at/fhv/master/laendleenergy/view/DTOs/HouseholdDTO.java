package at.fhv.master.laendleenergy.view.DTOs;

import java.util.UUID;

public class HouseholdDTO {
    private String householdId;
    private String pricingPlan;
    private String deviceId;
    private String incentive;
    private String savingTarget;

    public HouseholdDTO() {
    }

    public HouseholdDTO(String householdId, String pricingPlan, String deviceId, String incentive, String savingTarget) {
        this.householdId = householdId;
        this.pricingPlan = pricingPlan;
        this.deviceId = deviceId;
        this.incentive = incentive;
        this.savingTarget = savingTarget;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
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

    public String getIncentive() {
        return incentive;
    }

    public void setIncentive(String incentive) {
        this.incentive = incentive;
    }

    public String getSavingTarget() {
        return savingTarget;
    }

    public void setSavingTarget(String savingTarget) {
        this.savingTarget = savingTarget;
    }
}
