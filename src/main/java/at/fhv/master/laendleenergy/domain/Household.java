package at.fhv.master.laendleenergy.domain;

import java.util.Map;
import java.util.UUID;

public class Household {
    private String id;
    private ElectricityPricingPlan pricingPlan;
    private String deviceId;
    private String incentive;
    private String savingTarget;
    private Map<String, Member> members;

    public Household() {

    }

    public Household(String deviceId, ElectricityPricingPlan pricingPlan, String incentive, String savingTarget, Map<String, Member> members) {
        this.id =  UUID.randomUUID().toString();
        this.deviceId = deviceId;
        this.pricingPlan = pricingPlan;
        this.incentive = incentive;
        this.savingTarget = savingTarget;
        this.members = members;
    }

    public Household(String householdId, String deviceId, ElectricityPricingPlan pricingPlan, String incentive, String savingTarget, Map<String, Member> members) {
        this.id =  householdId;
        this.deviceId = deviceId;
        this.pricingPlan = pricingPlan;
        this.incentive = incentive;
        this.savingTarget = savingTarget;
        this.members = members;
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

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Household{" +
                ", deviceId='" + deviceId + '\'' +
                ", pricingPlan=" + pricingPlan +
                ", incentive='" + incentive + '\'' +
                ", savingTarget='" + savingTarget + '\'' +
                ", members=" + members +
                '}';
    }
}
