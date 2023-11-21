package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.MemberDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;

import java.time.LocalDate;

public interface HouseholdService {
    void createHousehold(HouseholdDTO householdDTO, UserDTO userDTO);
    void deleteHousehold(String householdId);
    void addHouseholdMember(String householdId, MemberDTO memberDTO);
    void removeHouseholdMember(String memberId, String householdId);
    Household getHouseholdById(String householdId);
}
