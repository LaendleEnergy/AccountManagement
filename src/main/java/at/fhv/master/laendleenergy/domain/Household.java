package at.fhv.master.laendleenergy.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "household")
public class Household {
    @Id
    @Column(name = "household_id")
    private String id;
    @Column(name = "pricing_plan")
    @Enumerated(EnumType.STRING)
    private ElectricityPricingPlan pricingPlan;
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "incentive")
    private String incentive;
    @Column(name = "saving_target")
    private String savingTarget;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members;

    public Household() {

    }

    public Household(String deviceId, ElectricityPricingPlan pricingPlan, String incentive, String savingTarget, List<Member> members) {
        this.id =  UUID.randomUUID().toString();
        this.deviceId = deviceId;
        this.pricingPlan = pricingPlan;
        this.incentive = incentive;
        this.savingTarget = savingTarget;
        this.members = members;
    }

    public Household(String householdId, String deviceId, ElectricityPricingPlan pricingPlan, String incentive, String savingTarget, List<Member> members) {
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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public void removeMember(String memberId) {
        members.removeIf(member -> member.getId().equals(memberId));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Household household = (Household) o;
        return Objects.equals(id, household.id) && pricingPlan == household.pricingPlan && Objects.equals(deviceId, household.deviceId) && Objects.equals(incentive, household.incentive) && Objects.equals(savingTarget, household.savingTarget) && Objects.equals(members, household.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pricingPlan, deviceId, incentive, savingTarget, members);
    }
}
