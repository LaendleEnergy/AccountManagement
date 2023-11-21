package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;

public interface HouseholdRepository {
    void addHousehold(Household household);
    void deleteHousehold(String householdId);
    Household getHouseholdById(String householdId);

}
