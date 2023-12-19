package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;

import java.util.List;

public interface HouseholdService {
    String createHousehold(CreateHouseholdDTO householdDTO);
    void deleteHousehold(String householdId) throws HouseholdNotFoundException;
    void updateHousehold(String householdId, HouseholdDTO householdDTO) throws HouseholdNotFoundException;
    HouseholdDTO getHouseholdById(String householdId) throws HouseholdNotFoundException;
    List<User> getUsersOfHousehold(String householdId) throws HouseholdNotFoundException;
}
