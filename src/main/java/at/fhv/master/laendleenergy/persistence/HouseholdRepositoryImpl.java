package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.*;
import at.fhv.master.laendleenergy.domain.exceptions.HouseholdNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.*;

@ApplicationScoped
public class HouseholdRepositoryImpl implements HouseholdRepository {

    private final Map<String, Household> households;
    MemberRepository memberRepository;

    @Inject
    public HouseholdRepositoryImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        households = new HashMap<>();
        String[] deviceIds = {"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10"};
        String[] incentives = {"10% off", "Free energy audit", "20% off", "Free smart thermostat", "15% off", "Free LED bulbs", "25% off", "Free energy-saving workshop", "30% off", "Free solar panel consultation"};
        String[] savingTargets = {"$200 per year", "$150 per year", "$300 per year", "$250 per year", "$180 per year", "$120 per year", "$350 per year", "$280 per year", "$400 per year", "$320 per year"};

        // Create and add households to the map
        for (int i = 0; i < 10; i++) {
            HashMap<String, Member> currentMembers = new HashMap<>();
            Member m = memberRepository.getAllMembers().get(i);
            currentMembers.put(m.getId(), m);

            Household household = new Household(
                    m.getHouseholdId(),
                    deviceIds[i],
                    i % 2 == 0 ? ElectricityPricingPlan.NORMAL : ElectricityPricingPlan.DAYNIGHT,
                    incentives[i],
                    savingTargets[i],
                    currentMembers
            );
            households.put(household.getId(), household);
        }
    }

    @Override
    public String addHousehold(Household household) {
        households.put(household.getId(), household);
        return household.getId();
    }

    @Override
    public void deleteHousehold(String householdId) throws HouseholdNotFoundException  {
        if (households.get(householdId) != null) {
            households.remove(householdId);
        } else {
            throw new HouseholdNotFoundException();
        }
    }

    @Override
    public void updateHousehold(Household household) throws HouseholdNotFoundException {
        if (households.get(household.getId()) != null) {
            households.replace(household.getId(), household);
        } else {
            throw new HouseholdNotFoundException();
        }
    }

    @Override
    public Household getHouseholdById(String householdId) throws HouseholdNotFoundException {
        Household household = households.get(householdId);

        if (household != null) {
            return household;
        }
        throw new HouseholdNotFoundException();
    }
}
