package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Member;

import java.util.List;
import java.util.Map;

public interface MemberRepository {
    void addHouseholdMember(Member member, String householdId);
    void removeHouseholdMember(String memberId, String householdId);
    Map<String,Member> getAllMembersOfHousehold(String householdId);
}
