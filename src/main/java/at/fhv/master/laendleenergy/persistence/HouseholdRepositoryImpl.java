package at.fhv.master.laendleenergy.persistence;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Gender;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Member;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.*;

@ApplicationScoped
public class HouseholdRepositoryImpl implements HouseholdRepository {

    private final Map<String, Household> households;

    public HouseholdRepositoryImpl() {
        List<Member> members = new ArrayList<>();
        households = new HashMap<>();

        members.add(new Member("Alice", Optional.of(LocalDate.of(1990, 5, 15)), Optional.of(Gender.FEMALE)));
        members.add(new Member("Bob",  Optional.of(LocalDate.of(1985, 8, 22)),  Optional.of(Gender.MALE)));
        members.add(new Member("Charlie", Optional.of(LocalDate.of(1992, 2, 10)),  Optional.of(Gender.MALE)));
        members.add(new Member("David", Optional.of(LocalDate.of(1988, 11, 30)),  Optional.of(Gender.MALE)));
        members.add(new Member("Emma", Optional.of(LocalDate.of(1995, 4, 5)),  Optional.of(Gender.FEMALE)));
        members.add(new Member("Frank", Optional.of(LocalDate.of(1983, 7, 18)),  Optional.of(Gender.MALE)));
        members.add(new Member("Grace", Optional.of(LocalDate.of(1998, 9, 12)),  Optional.of(Gender.FEMALE)));
        members.add(new Member("Henry", Optional.of(LocalDate.of(1982, 12, 8)),  Optional.of(Gender.MALE)));
        members.add(new Member("Ivy", Optional.of(LocalDate.of(1993, 6, 25)),  Optional.of(Gender.FEMALE)));
        members.add(new Member("Jack", Optional.of(LocalDate.of(1987, 3, 14)),  Optional.of(Gender.MALE)));


        String[] deviceIds = {"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10"};
        String[] incentives = {"10% off", "Free energy audit", "20% off", "Free smart thermostat", "15% off", "Free LED bulbs", "25% off", "Free energy-saving workshop", "30% off", "Free solar panel consultation"};
        String[] savingTargets = {"$200 per year", "$150 per year", "$300 per year", "$250 per year", "$180 per year", "$120 per year", "$350 per year", "$280 per year", "$400 per year", "$320 per year"};

        // Create and add households to the map
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            HashMap<String, Member> currentMembers = new HashMap<>();
            int id = random.nextInt(0, 10);
            currentMembers.put(members.get(id).getId(), members.get(id));
            currentMembers.put(members.get(id).getId(), members.get(id));
            currentMembers.put(members.get(id).getId(), members.get(id));

            Household household = new Household(
                    i % 2 == 0 ? ElectricityPricingPlan.NORMAL : ElectricityPricingPlan.DAYNIGHT,
                    deviceIds[i],
                    incentives[i],
                    savingTargets[i],
                    currentMembers
            );
            households.put(deviceIds[i], household);
        }
    }

    @Override
    public void addHousehold(Household household) {
        households.put(household.getHouseholdId(), household);
    }

    @Override
    public void deleteHousehold(String householdId) {
        households.remove(householdId);
    }

    @Override
    public void updateHousehold(Household household) {
        households.replace(household.getHouseholdId(), household);
    }

    @Override
    public Household getHouseholdById(String householdId) {
        return households.get(householdId);
    }

}
