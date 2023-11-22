package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class HouseholdServiceImpl implements HouseholdService {

    @Inject
    HouseholdRepository householdRepository;

    @Override
    public void createHousehold(HouseholdDTO householdDTO, UserDTO userDTO) {
        User user = User.create(userDTO);

        Map<String, Member> members = new HashMap<>();
        members.put(user.getId(), user);

        Household household = Household.create(householdDTO, members);
        householdRepository.addHousehold(household);
    }

    @Override
    public void deleteHousehold(String householdId) {
        householdRepository.deleteHousehold(householdId);
    }

    @Override
    public Household getHouseholdById(String householdId) {
        return householdRepository.getHouseholdById(householdId);
    }
}
