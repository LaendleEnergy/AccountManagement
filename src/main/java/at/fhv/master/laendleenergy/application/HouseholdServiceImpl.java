package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class HouseholdServiceImpl implements HouseholdService {

    private HouseholdRepository householdRepository;

    @Override
    public void createHousehold(String emailAddress, String name, String password, ElectricityPricingPlan pricingPlan, String deviceId) {
        User user = new User(emailAddress, password, Role.ADMIN, name, Optional.empty(), Optional.empty());
        Map<String, Member> members = new HashMap<>();
        members.put(user.getId(), user);

        Household household = new Household(pricingPlan, deviceId, "", "", members);
        householdRepository.addHousehold(household);
    }

    @Override
    public void deleteHousehold(String householdId) {
        householdRepository.deleteHousehold(householdId);
    }

    @Override
    public void addHouseholdMember(String householdId, String name, LocalDate dateOfBirth, Gender gender) {
        Member member = new Member(name, Optional.of(dateOfBirth), Optional.of(gender));
        Household household = householdRepository.getHouseholdById(householdId);
        household.addMember(member);

        householdRepository.addHouseholdMember(member, householdId);
    }

    @Override
    public void removeHouseholdMember(String memberId, String householdId) {
        Household household = householdRepository.getHouseholdById(householdId);
        household.removeMember(memberId);

        householdRepository.removeHouseholdMember(memberId, householdId);
    }

    @Override
    public Household getHouseholdById(String householdId) {
        return householdRepository.getHouseholdById(householdId);
    }
}
