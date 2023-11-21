package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
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
    public void addHouseholdMember(Member member, String householdId) {
        Household h = households.get(householdId);
        h.addMember(member);
    }

    @Override
    public void removeHouseholdMember(String memberId, String householdId) {
        Household h = households.get(householdId);
        h.removeMember(memberId);
    }

    @Override
    public Household getHouseholdById(String householdId) {
        Household h = households.get(householdId);
        return h;
    }
}
