package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.authentication.PBKDF2Encoder;
import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.persistence.UserRepository;
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
    @Inject
    UserRepository userRepository;
    @Inject
    PBKDF2Encoder passwordEncoder;

    @Override
    public void createHousehold(CreateHouseholdDTO householdDTO) {
        User user = new User(householdDTO.getEmailAddress(), passwordEncoder.encode(householdDTO.getPassword()), Role.ADMIN, householdDTO.getName(), Optional.empty(), Optional.empty());

        Map<String, Member> members = new HashMap<>();
        members.put(user.getId(), user);

        Household household = new Household(householdDTO.getDeviceId(), ElectricityPricingPlan.get(householdDTO.getPricingPlan()), "", "", members);
        user.setHousehold(household);

        userRepository.addUser(user);
        householdRepository.addHousehold(household);
    }

    @Override
    public void deleteHousehold(String deviceId) {
        householdRepository.deleteHousehold(deviceId);
    }

    @Override
    public void updateHousehold(HouseholdDTO householdDTO) {
        Household oldHousehold = householdRepository.getHouseholdById(householdDTO.getDeviceId());
        householdRepository.updateHousehold(HouseholdDTO.create(householdDTO, oldHousehold.getIncentive(), oldHousehold.getSavingTarget(), memberRepository.getAllMembersOfHousehold(householdDTO.getDeviceId())));
    }

    @Override
    public HouseholdDTO getHouseholdById(String deviceId) {
        return HouseholdDTO.create(householdRepository.getHouseholdById(deviceId));
    }
}
