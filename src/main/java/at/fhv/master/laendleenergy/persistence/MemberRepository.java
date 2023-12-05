package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;
import java.util.Map;

public interface MemberRepository {
    void addHouseholdMember(Member member) throws HouseholdNotFoundException;
    void removeHouseholdMember(String memberId, String householdId) throws HouseholdNotFoundException, MemberNotFoundException;
    Map<String,Member> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException;
}
