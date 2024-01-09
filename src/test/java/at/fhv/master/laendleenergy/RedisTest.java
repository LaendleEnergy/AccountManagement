package at.fhv.master.laendleenergy;

import at.fhv.master.laendleenergy.domain.ElectricityPricingPlan;
import at.fhv.master.laendleenergy.domain.Household;
import at.fhv.master.laendleenergy.domain.events.HouseholdUpdatedEvent;
import at.fhv.master.laendleenergy.domain.serializer.HouseholdSerializer;
import at.fhv.master.laendleenergy.streams.publisher.HouseholdUpdatedEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;

// Start Redis:
//`docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest`

@QuarkusTest
public class RedisTest {
    @Inject
    HouseholdUpdatedEventPublisher pub;

    @Test
    public void testConnection() throws JsonProcessingException {
        Household household = new Household("123", ElectricityPricingPlan.NORMAL, new LinkedList<>());
        HouseholdUpdatedEvent event = new HouseholdUpdatedEvent("event1", household, LocalDateTime.now());
        pub.publishMessage(HouseholdSerializer.parse(event));
    }
}
