package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;

public interface HouseholdRepository {
    void addHousehold(Household household);
    void deleteHousehold(String householdId);
    void addHouseholdMember(Member member, String householdId);
    void removeHouseholdMember(String memberId, String householdId);
    Household getHouseholdById(String householdId);
}
