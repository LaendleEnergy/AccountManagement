package at.fhv.master.laendleenergy.application.publisher;

import at.fhv.master.laendleenergy.application.streams.publisher.MemberAddedEventPublisher;
import at.fhv.master.laendleenergy.application.streams.publisher.MemberUpdatedEventPublisher;
import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.events.MemberUpdatedEvent;
import at.fhv.master.laendleenergy.domain.serializer.MemberAddedSerializer;
import at.fhv.master.laendleenergy.domain.serializer.MemberUpdatedSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@QuarkusTest
@TestTransaction
public class MemberUpdatedEventPublisherTests {
    @Inject
    MemberUpdatedEventPublisher pub;

    @Test
    public void testConnection() throws JsonProcessingException {
        MemberUpdatedEvent event = new MemberUpdatedEvent("event1", "memberid", "name", "h1", LocalDateTime.now());
        pub.publishMessage(MemberUpdatedSerializer.parse(event));
    }
}
