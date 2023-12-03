package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;

public interface HouseholdRepository {
    void addHousehold(Household household);
    void deleteHousehold(String deviceId);
    void updateHousehold(Household household);
    Household getHouseholdById(String deviceId);
}
