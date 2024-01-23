package at.fhv.master.laendleenergy.application.publisher;

import at.fhv.master.laendleenergy.application.streams.publisher.MemberRemovedEventPublisher;
import at.fhv.master.laendleenergy.domain.events.MemberRemovedEvent;
import at.fhv.master.laendleenergy.domain.serializer.MemberRemovedSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@QuarkusTest
@TestTransaction
public class MemberRemovedEventPublisherTests {
    @Inject
    MemberRemovedEventPublisher pub;

    @Test
    public void testConnection() throws JsonProcessingException {
        MemberRemovedEvent event = new MemberRemovedEvent("event1", "memberid", "h1", LocalDateTime.now());
        pub.publishMessage(MemberRemovedSerializer.parse(event));
    }
}
