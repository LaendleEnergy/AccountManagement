package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;

@ApplicationScoped
public class MemberRepositoryImpl implements MemberRepository {

    @Inject
    HouseholdRepository householdRepository;

    @Override
    public void addHouseholdMember(Member member, String householdId) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);

        if (household != null) {
            household.addMember(member);
        } else {
            throw new HouseholdNotFoundException();
        }
    }

    @Override
    public void removeHouseholdMember(String memberId, String householdId) throws HouseholdNotFoundException {
        Household household = householdRepository.getHouseholdById(householdId);

        if (household != null) {
            household.removeMember(memberId);
        } else {
            throw new HouseholdNotFoundException();
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
