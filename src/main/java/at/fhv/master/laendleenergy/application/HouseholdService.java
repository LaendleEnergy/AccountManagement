package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;

public interface HouseholdService {
    void createHousehold(CreateHouseholdDTO createHouseholdDTO);
    void deleteHousehold(String householdId);
    void updateHousehold(String householdId, HouseholdDTO householdDTO);
    HouseholdDTO getHouseholdById(String householdId);
}
