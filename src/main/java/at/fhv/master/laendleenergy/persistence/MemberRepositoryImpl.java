package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class MemberRepositoryImpl implements MemberRepository {

    @Inject
    HouseholdRepository householdRepository;

    @Override
    public void addHouseholdMember(Member member, String householdId) {
        Household household = householdRepository.getHouseholdById(householdId);
        household.addMember(member);
    }

    @Override
    public void removeHouseholdMember(String memberId, String householdId) {
        Household household = householdRepository.getHouseholdById(householdId);
        household.removeMember(memberId);
    }

    @Override
    public Map<String,Member> getAllMembersOfHousehold(String householdId) {
        return householdRepository.getHouseholdById(householdId).getMembers();
    }
}
