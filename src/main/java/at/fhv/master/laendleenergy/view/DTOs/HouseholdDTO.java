package at.fhv.master.laendleenergy.view.DTOs;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.User;

import java.util.List;

public class HouseholdDTO {
    private String deviceId;
    private String pricingPlan;
    private String supplier;


    public HouseholdDTO() {}

    public HouseholdDTO(String deviceId, String pricingPlan, String supplier) {
        this.deviceId = deviceId;
        this.pricingPlan = pricingPlan;
        this.supplier = supplier;
    }

    public static Household create(String id, HouseholdDTO household, List<Member> members) {
        return new Household(
                id,
                household.getDeviceId(),
                ElectricityPricingPlan.get(household.getPricingPlan()),
                members);
    }

    public static HouseholdDTO create(Household household) {
        return new HouseholdDTO(household.getDeviceId(), household.getPricingPlan().getName(), household.getPricingPlan().getSupplier().getName());
    }

    public static Household create(HouseholdDTO householdDTO, List<Member> members) {
        return new Household(
                householdDTO.getDeviceId(),
                ElectricityPricingPlan.get(householdDTO.getPricingPlan()),
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
}
