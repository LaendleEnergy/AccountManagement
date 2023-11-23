package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    public List<Member> getAllMembersOfHousehold(String householdId) {
        return new LinkedList<>(householdRepository.getHouseholdById(householdId).getMembers().values());
    }
}
