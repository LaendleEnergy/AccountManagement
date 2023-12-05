package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;

public interface HouseholdService {
    void createHousehold(CreateHouseholdDTO householdDTO);
    void deleteHousehold(String deviceId) throws HouseholdNotFoundException;
    void updateHousehold(HouseholdDTO householdDTO) throws HouseholdNotFoundException;
    HouseholdDTO getHouseholdById(String deviceId) throws HouseholdNotFoundException;
}
