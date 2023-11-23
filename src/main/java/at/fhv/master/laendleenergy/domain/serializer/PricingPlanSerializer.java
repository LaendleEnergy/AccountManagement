package at.fhv.master.laendleenergy.domain.serializer;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedList;
import java.util.List;

public class PricingPlanSerializer {

    public PricingPlanSerializer() {}

    public static List<String> parse() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> parsedPlans = new LinkedList<>();

        for (ElectricityPricingPlan e : ElectricityPricingPlan.values()) {
            parsedPlans.add(objectMapper.writeValueAsString(e));
        }

        return parsedPlans;
    }
}