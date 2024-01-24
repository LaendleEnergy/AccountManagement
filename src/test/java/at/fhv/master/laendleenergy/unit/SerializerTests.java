package at.fhv.master.laendleenergy.unit;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.events.*;
import at.fhv.master.laendleenergy.domain.serializer.*;
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
    public void testHouseholdUpdatedSerializer() throws JsonProcessingException {
        Household household = new Household("id", "123", ElectricityPricingPlan.NORMAL, new LinkedList<>());
        HouseholdUpdatedEvent event = new HouseholdUpdatedEvent("event1", household.getId(), LocalDateTime.of(2020, 1,1,1,1));

        String expected = "{\"eventId\":\"event1\",\"householdId\":\"id\",\"timestamp\":[2020,1,1,1,1]}";
        String actual = HouseholdUpdatedSerializer.parse(event);

        assertEquals(expected, actual);
    }

    @Test
    public void testHouseholdCreatedSerializer() throws JsonProcessingException {
        HouseholdCreatedEvent event = new HouseholdCreatedEvent("event1", "m1", "name", "h1", LocalDateTime.of(2020, 1,1,1,1));

        String expected = "{\"eventId\":\"event1\",\"householdId\":\"h1\",\"timestamp\":[2020,1,1,1,1],\"memberId\":\"m1\",\"name\":\"name\"}";
        String actual = HouseholdCreatedSerializer.parse(event);

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
    public void testMemberAddedSerializer() throws JsonProcessingException {
        MemberAddedEvent event = new MemberAddedEvent("event1", "m1", "name", "h1", LocalDateTime.of(2020, 1,1,1,1));

        String expected = "{\"eventId\":\"event1\",\"householdId\":\"h1\",\"timestamp\":[2020,1,1,1,1],\"memberId\":\"m1\",\"name\":\"name\"}";
        String actual = MemberAddedSerializer.parse(event);

        assertEquals(expected, actual);
    }
    @Test
    public void testMemberUpdatedSerializer() throws JsonProcessingException {
        MemberUpdatedEvent event = new MemberUpdatedEvent("event1", "m1", "name", "h1", LocalDateTime.of(2020, 1,1,1,1));

        String expected = "{\"eventId\":\"event1\",\"householdId\":\"h1\",\"timestamp\":[2020,1,1,1,1],\"memberId\":\"m1\",\"name\":\"name\"}";
        String actual = MemberUpdatedSerializer.parse(event);

        assertEquals(expected, actual);
    }

    @Test
    public void testMemberRemovedSerializer() throws JsonProcessingException {
        MemberRemovedEvent event = new MemberRemovedEvent("event1", "m1", "h1", LocalDateTime.of(2020, 1,1,1,1));

        String expected = "{\"eventId\":\"event1\",\"householdId\":\"h1\",\"timestamp\":[2020,1,1,1,1],\"memberId\":\"m1\"}";
        String actual = MemberRemovedSerializer.parse(event);

        assertEquals(expected, actual);
    }

}
