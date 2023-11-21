package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class HouseholdRepositoryImpl implements HouseholdRepository {

    private final Map<String, Household> households = new HashMap<>();

    @Override
    public void addHousehold(Household household) {
        households.put(household.getHouseholdId(), household);
    }

    @Override
    public void deleteHousehold(String householdId) {
        households.remove(householdId);
    }

    @Override
    public Household getHouseholdById(String householdId) {
        return households.get(householdId);
    }

}
