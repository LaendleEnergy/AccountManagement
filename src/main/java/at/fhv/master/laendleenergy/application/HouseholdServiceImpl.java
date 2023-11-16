package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.persistence.HouseholdRepository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

public class HouseholdServiceImpl implements HouseholdService {

    private HouseholdRepository householdRepository;

    @Override
    public void createHousehold(String emailAddress, String name, String password, ElectricityPricingPlan pricingPlan, String deviceId) {
        User user = new User(emailAddress, password, Role.ADMIN, name, Optional.empty(), Optional.empty());
        LinkedList<Member> members = new LinkedList<>();
        members.add(user);

        Household household = new Household(pricingPlan, deviceId, "", "", members);
        householdRepository.createHousehold(household);
    }

    @Override
    public void deleteHousehold(String householdId) {
        householdRepository.deleteHousehold(householdId);
    }

    @Override
    public void addHouseholdMember(String name, LocalDate dateOfBirth, Gender gender) {
        Member member = new Member(name, Optional.of(dateOfBirth), Optional.of(gender));
        householdRepository.addHouseholdMember(member);
    }

    @Override
    public void removeHouseholdMember(String memberId) {
        householdRepository.removeHouseholdMember(memberId);
    }
}
