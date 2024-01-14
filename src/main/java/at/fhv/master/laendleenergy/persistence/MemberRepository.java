package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Member;
import at.fhv.master.laendleenergy.domain.User;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import at.fhv.master.laendleenergy.domain.exceptions.MemberNotFoundException;

import java.util.List;

public interface MemberRepository {
    void addHouseholdMember(Member member);
    void removeHouseholdMember(String memberId) throws MemberNotFoundException;
    List<Member> getAllMembersOfHousehold(String householdId) throws HouseholdNotFoundException;
    Member getMemberById(String memberId) throws MemberNotFoundException;
    void updateMember(Member member) throws MemberNotFoundException;
}
