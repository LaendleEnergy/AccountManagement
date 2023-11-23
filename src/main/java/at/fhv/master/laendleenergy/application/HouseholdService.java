package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;

import java.util.Map;

public interface HouseholdService {
    void createHousehold(HouseholdDTO householdDTO, UserDTO userDTO);
    void deleteHousehold(String householdId);
    void updateHousehold(HouseholdDTO householdDTO, Map<String, Member> members);
    Household getHouseholdById(String householdId);
}
