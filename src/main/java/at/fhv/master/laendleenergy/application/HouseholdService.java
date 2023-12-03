package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;

public interface HouseholdService {
    void createHousehold(CreateHouseholdDTO householdDTO);
    void deleteHousehold(String deviceId);
    void updateHousehold(HouseholdDTO householdDTO);
    HouseholdDTO getHouseholdById(String deviceId);
}
