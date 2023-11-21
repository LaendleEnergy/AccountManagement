package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class HouseholdServiceImpl implements HouseholdService {

    @Inject
    HouseholdRepository householdRepository;

    @Override
    public void createHousehold(HouseholdDTO householdDTO, UserDTO userDTO) {
        User user = new User(userDTO.getEmailAddress(), userDTO.getPassword(), Role.valueOf(userDTO.getRole()), userDTO.getName(), Optional.of(LocalDate.parse(userDTO.getDateOfBirth())), Optional.of(Gender.valueOf(userDTO.getGender())));

        Map<String, Member> members = new HashMap<>();
        members.put(user.getId(), user);
        Household household = new Household(ElectricityPricingPlan.valueOf(householdDTO.getPricingPlan()), householdDTO.getDeviceId(), householdDTO.getIncentive(), householdDTO.getSavingTarget(), members);
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
