package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Gender;

import java.time.LocalDate;

public interface HouseholdService {
    void createHousehold(String emailAddress, String name, String password, ElectricityPricingPlan pricingPlan, String deviceId);
    void deleteHousehold(String householdId);
    void addHouseholdMember(String name, LocalDate dateOfBirth, Gender gender);
    void removeHouseholdMember(String memberId);
}
