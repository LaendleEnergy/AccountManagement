package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;

@ApplicationScoped
public class MemberRepositoryImpl implements MemberRepository {

    @Inject
    HouseholdRepository householdRepository;

    @Override
    public void addHouseholdMember(Member member) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(member.getDeviceId());

        if (household != null) {
            household.addMember(member);
        } else {
            throw new HouseholdNotFoundException();
        }
    }

    @Override
    public void removeHouseholdMember(String memberId, String householdId) throws HouseholdNotFoundException, MemberNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);

        if (household == null) {
            throw new HouseholdNotFoundException();
        } else if (!household.getMembers().containsKey(memberId)) {
            throw new MemberNotFoundException();
        } else {
            household.removeMember(memberId);
        }
    }

    @Override
    public Map<String,Member> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);

        if (household != null) {
            return householdRepository.getHouseholdById(householdId).getMembers();
        } else {
            throw new HouseholdNotFoundException();
        }
    }
}
