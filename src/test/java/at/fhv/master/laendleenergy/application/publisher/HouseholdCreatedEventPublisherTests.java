package at.fhv.master.laendleenergy.application.publisher;

import at.fhv.master.laendleenergy.application.streams.publisher.HouseholdCreatedEventPublisher;
import at.fhv.master.laendleenergy.domain.events.HouseholdCreatedEvent;
import at.fhv.master.laendleenergy.domain.serializer.HouseholdCreatedSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

@QuarkusTest
@TestTransaction
public class HouseholdCreatedEventPublisherTests {
    @Inject
    HouseholdCreatedEventPublisher pub;

    @Test
    public void testConnection() throws JsonProcessingException {
        HouseholdCreatedEvent event = new HouseholdCreatedEvent("event1", "memberid", "name", "h1", LocalDateTime.now());
        pub.publishMessage(HouseholdCreatedSerializer.parse(event));
    }
}
