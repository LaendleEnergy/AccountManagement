package at.fhv.master.laendleenergy.domain;

import java.util.List;
import java.util.UUID;

public class Household {
    private final String householdId;
    private ElectricityPricingPlan pricingPlan;
    private String deviceId;
    private String incentive;
    private String savingTarget;
    private List<Member> members;

    public Household() {
        this.householdId =  UUID.randomUUID().toString();
    }

    public Household(ElectricityPricingPlan pricingPlan, String deviceId, String incentive, String savingTarget, List<Member> members) {
        this.householdId =  UUID.randomUUID().toString();
        this.pricingPlan = pricingPlan;
        this.deviceId = deviceId;
        this.incentive = incentive;
        this.savingTarget = savingTarget;
        this.members = members;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public ElectricityPricingPlan getPricingPlan() {
        return pricingPlan;
    }

    public void setPricingPlan(ElectricityPricingPlan pricingPlan) {
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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
