package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;

public interface HouseholdRepository {
    void addHousehold(Household household);
    void deleteHousehold(String deviceId) throws HouseholdNotFoundException;
    void updateHousehold(Household household) throws HouseholdNotFoundException;
    Household getHouseholdById(String deviceId) throws HouseholdNotFoundException;
}
