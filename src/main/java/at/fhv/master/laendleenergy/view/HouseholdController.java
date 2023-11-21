package at.fhv.master.laendleenergy.view;

import at.fhv.master.laendleenergy.application.HouseholdService;
import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.Supplier;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.*;

@Path("/household")
public class HouseholdController {

    private final Map<String, Household> households = new HashMap<>();

    public HouseholdController() {
        households.put("HouseholdID1", new Household(new ElectricityPricingPlan(Supplier.VKW, 12.45, "Mein Haushalt"), "1234", "blabla", "123bla", new HashMap<>()));
        households.put("HouseholdID2", new Household(new ElectricityPricingPlan(Supplier.VKW, 15.45, "Mein Haushalt Nr. 2"), "9090", "blabla2", "123bla2", new HashMap<>()));
    }

    @Inject
    HouseholdService householdService;

    @POST
    public Map<String, Household> add(Household household) {
        households.put(UUID.randomUUID().toString(), household);
        return households;
    }

    @GET
    @Path("/get/{householdId}")
    public Household getHouseholdById(String householdId) {
        return households.get(householdId);
        //return householdService.getHouseholdById(householdId);
    }
}
