package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import java.util.Map;

public class HouseholdDTO {
    private String deviceId;
    private String pricingPlan;
    private String supplier;
    private String incentive;
    private String savingTarget;


    public HouseholdDTO() {}

    public HouseholdDTO(String deviceId, String pricingPlan, String supplier, String incentive, String savingTarget) {
        this.deviceId = deviceId;
        this.pricingPlan = pricingPlan;
        this.supplier = supplier;
        this.incentive = incentive;
        this.savingTarget = savingTarget;
    }

    public static Household create(HouseholdDTO household, String incentive, String savingTarget, Map<String, Member> members) {
        return new Household(
                household.getDeviceId(),
                ElectricityPricingPlan.get(household.getPricingPlan()),
                incentive,
                savingTarget,
                members);
    }

    public static HouseholdDTO create(Household household) {
        return new HouseholdDTO(household.getDeviceId(), household.getPricingPlan().getName(), household.getPricingPlan().getSupplier().getName(), household.getIncentive(), household.getSavingTarget());
    }

    public static Household create(HouseholdDTO householdDTO, Map<String, Member> members) {
        return new Household(
                householdDTO.getDeviceId(),
                ElectricityPricingPlan.get(householdDTO.getPricingPlan()),
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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
