package at.fhv.master.laendleenergy.unit;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.events.HouseholdUpdatedEvent;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.serializer.HouseholdSerializer;
import at.fhv.master.laendleenergy.domain.serializer.MemberSerializer;
import at.fhv.master.laendleenergy.domain.serializer.PricingPlanSerializer;
import at.fhv.master.laendleenergy.domain.serializer.SupplierSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@QuarkusTest
public class SerializerTests {

    @Test
    public void testHouseholdSerializer() throws JsonProcessingException {
        Household household = new Household("id", "123", ElectricityPricingPlan.NORMAL, new LinkedList<>());
        HouseholdUpdatedEvent event = new HouseholdUpdatedEvent("event1", household, LocalDateTime.of(2020, 1,1,1,1));

        String expected = "{\"eventId\":\"event1\",\"household\":{\"id\":\"id\",\"pricingPlan\":{\"supplier\":\"VKW\",\"averagePricePerKwh\":14.76,\"name\":\"Normal\"},\"deviceId\":\"123\",\"members\":[]},\"timestamp\":[2020,1,1,1,1]}";
        String actual = HouseholdSerializer.parse(event);

        assertEquals(expected, actual);
    }

    @Test
    public void testPricingPlanSerializer() throws JsonProcessingException {
        List<String> expected = List.of("{\"supplier\":\"VKW\",\"averagePricePerKwh\":12.12,\"name\":\"Tag/Nacht\"}", "{\"supplier\":\"VKW\",\"averagePricePerKwh\":14.76,\"name\":\"Normal\"}");
        List<String> actual = PricingPlanSerializer.parse();

        assertEquals(expected, actual);
    }

    @Test
    public void testSupplierSerializer() throws JsonProcessingException {
        List<String> expected = List.of("\"VKW\"");
        List<String> actual = SupplierSerializer.parse();

        assertEquals(expected, actual);
    }

    @Test
    public void testMemberSerializer() throws JsonProcessingException {
        MemberAddedEvent event = new MemberAddedEvent("event1", "m1", "name", "h1", LocalDateTime.of(2020, 1,1,1,1));

        String expected = "{\"eventId\":\"event1\",\"memberId\":\"m1\",\"name\":\"name\",\"householdId\":\"h1\",\"timestamp\":[2020,1,1,1,1]}";
        String actual = MemberSerializer.parse(event);

        assertEquals(expected, actual);
    }

}
