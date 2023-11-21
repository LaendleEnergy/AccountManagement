package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Member;

import java.util.List;

public interface MemberRepository {
    void addHouseholdMember(Member member, String householdId);
    void removeHouseholdMember(String memberId, String householdId);
    List<Member> getAllMembersOfHousehold(String householdId);
}
