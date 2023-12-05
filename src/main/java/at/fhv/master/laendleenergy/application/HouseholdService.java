package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;

public interface HouseholdService {
    String createHousehold(CreateHouseholdDTO householdDTO);
    void deleteHousehold(String householdId) throws HouseholdNotFoundException;
    void updateHousehold(HouseholdDTO householdDTO) throws HouseholdNotFoundException;
    HouseholdDTO getHouseholdById(String householdId) throws HouseholdNotFoundException;
}
