package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;

public interface HouseholdService {
    void createHousehold(HouseholdDTO householdDTO, UserDTO userDTO);
    void deleteHousehold(String householdId);
    Household getHouseholdById(String householdId);
}
