package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface HouseholdService {
    String createHousehold(CreateHouseholdDTO householdDTO);
    void deleteHousehold(String householdId) throws HouseholdNotFoundException;
    void updateHousehold(String householdId, HouseholdDTO householdDTO) throws HouseholdNotFoundException, JsonProcessingException;
    HouseholdDTO getHouseholdById(String householdId) throws HouseholdNotFoundException;
    List<UserDTO> getUsersOfHousehold(String householdId) throws HouseholdNotFoundException;
}
