package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;

import java.util.Map;

public class HouseholdDTO {
    private String pricingPlan;
    private String deviceId;
    private String incentive;
    private String savingTarget;

    public HouseholdDTO() {
    }

    public HouseholdDTO(String pricingPlan, String deviceId, String incentive, String savingTarget) {
        this.pricingPlan = pricingPlan;
        this.deviceId = deviceId;
        this.incentive = incentive;
        this.savingTarget = savingTarget;
    }

    public HouseholdDTO(String pricingPlan, String deviceId) {
        this.pricingPlan = pricingPlan;
        this.deviceId = deviceId;
    }

    public static HouseholdDTO create(Household household) {
        return new HouseholdDTO(household.getPricingPlan().getName(), household.getDeviceId(), household.getIncentive(), household.getSavingTarget());
    }

    public static Household create(String householdId, HouseholdDTO householdDTO, Map<String, Member> members) {
        return new Household(
                householdId,
                ElectricityPricingPlan.get(householdDTO.getPricingPlan()),
                householdDTO.getDeviceId(),
                householdDTO.getIncentive(),
                householdDTO.getSavingTarget(),
                members
        );
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
