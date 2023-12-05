package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;

import java.util.List;
import java.util.Map;

public interface MemberRepository {
    void addHouseholdMember(Member member, String householdId) throws HouseholdNotFoundException;
    void removeHouseholdMember(String memberId, String householdId) throws HouseholdNotFoundException;
    Map<String,Member> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException;
}
