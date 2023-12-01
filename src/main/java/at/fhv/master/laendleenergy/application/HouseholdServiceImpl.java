package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class HouseholdServiceImpl implements HouseholdService {

    @Inject
    HouseholdRepository householdRepository;
    @Inject
    MemberRepository memberRepository;

    @Override
    public void createHousehold(CreateHouseholdDTO createHouseholdDTO) {
        User user = new User(createHouseholdDTO.getEmail(), createHouseholdDTO.getPassword(), Role.ADMIN, createHouseholdDTO.getName(), Optional.empty(), Optional.empty());

        Map<String, Member> members = new HashMap<>();
        members.put(user.getId(), user);

        Household household = new Household(ElectricityPricingPlan.get(createHouseholdDTO.getPricingPlan()), createHouseholdDTO.getDeviceId(), "", "", members);

        householdRepository.addHousehold(household);
    }

    @Override
    public void deleteHousehold(String householdId) {
        householdRepository.deleteHousehold(householdId);
    }

    @Override
    public void updateHousehold(String householdId, HouseholdDTO householdDTO) {
        householdRepository.updateHousehold(HouseholdDTO.create(householdId, householdDTO, memberRepository.getAllMembersOfHousehold(householdId)));
    }

    @Override
    public HouseholdDTO getHouseholdById(String householdId) {
        return HouseholdDTO.create(householdRepository.getHouseholdById(householdId));
    }

}
