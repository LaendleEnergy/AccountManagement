package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;

public interface HouseholdRepository {
    void createHousehold(Household household);
    void deleteHousehold(String householdId);
    void addHouseholdMember(Member member);
    void removeHouseholdMember(String memberId);
}
