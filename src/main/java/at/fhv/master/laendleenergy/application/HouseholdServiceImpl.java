package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.application.authentication.PBKDF2Encoder;
import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import at.fhv.master.laendleenergy.persistence.MemberRepository;
import at.fhv.master.laendleenergy.persistence.UserRepository;
import at.fhv.master.laendleenergy.view.DTOs.CreateHouseholdDTO;
import at.fhv.master.laendleenergy.view.DTOs.HouseholdDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.*;

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
    @Transactional
    public String createHousehold(CreateHouseholdDTO householdDTO) {
        User user = new User(householdDTO.getEmailAddress(), passwordEncoder.encode(householdDTO.getPassword()), Role.ADMIN, householdDTO.getName(), Optional.empty(), Optional.empty(), null);

        List<Member> members = new ArrayList<>();
        members.add(user);

        Household household = new Household(householdDTO.getDeviceId(), ElectricityPricingPlan.get(householdDTO.getPricingPlan()), "", "", members);
        user.setHousehold(household);

        userRepository.addUser(user);
        return householdRepository.addHousehold(household);
    }

    @Override
    @Transactional
    public void deleteHousehold(String householdId) throws HouseholdNotFoundException {
        householdRepository.deleteHousehold(householdId);
    }

    @Override
    @Transactional
    public void updateHousehold(String householdId, HouseholdDTO householdDTO) throws HouseholdNotFoundException {
        Household oldHousehold = householdRepository.getHouseholdById(householdId);
        householdRepository.updateHousehold(HouseholdDTO.create(householdId, householdDTO, oldHousehold.getIncentive(), oldHousehold.getSavingTarget(), memberRepository.getAllMembersOfHousehold(householdId)));
    }

    @Override
    public HouseholdDTO getHouseholdById(String householdId) throws HouseholdNotFoundException {
        return HouseholdDTO.create(householdRepository.getHouseholdById(householdId));
    }

    @Override
    public List<User> getUsersOfHousehold(String householdId) throws HouseholdNotFoundException {
        List<User> users = new LinkedList<>();

        for (Member m : memberRepository.getAllMembersOfHousehold(householdId)) {
            if (m instanceof User) {
                users.add((User) m);
            }
        }

        return users;
    }
}
