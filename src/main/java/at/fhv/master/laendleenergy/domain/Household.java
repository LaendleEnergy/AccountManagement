package at.fhv.master.laendleenergy.domain;

import java.util.Map;
import java.util.UUID;

public class Household {
    private String householdId;
    private ElectricityPricingPlan pricingPlan;
    private String deviceId;
    private String incentive;
    private String savingTarget;
    private Map<String, Member> members;

    public Household() {
        this.householdId =  UUID.randomUUID().toString();
    }

    public Household(ElectricityPricingPlan pricingPlan, String deviceId, String incentive, String savingTarget, Map<String, Member> members) {
        this.householdId = UUID.randomUUID().toString();
        this.pricingPlan = pricingPlan;
        this.deviceId = deviceId;
        this.incentive = incentive;
        this.savingTarget = savingTarget;
        this.members = members;
    }

    public Household(String householdId, ElectricityPricingPlan pricingPlan, String deviceId, String incentive, String savingTarget, Map<String, Member> members) {
        this.householdId = householdId;
        this.pricingPlan = pricingPlan;
        this.deviceId = deviceId;
        this.incentive = incentive;
        this.savingTarget = savingTarget;
        this.members = members;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
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

    public Map<String, Member> getMembers() {
        return members;
    }

    public void setMembers(Map<String, Member> members) {
        this.members = members;
    }

    public void addMember(Member member) {
        this.members.put(member.getId(), member);
    }

    public void removeMember(String memberId) {
        this.members.remove(memberId);
    }
}
