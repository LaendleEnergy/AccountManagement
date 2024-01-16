package at.fhv.master.laendleenergy.application.publisher;

import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import at.fhv.master.laendleenergy.domain.serializer.MemberAddedSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

@QuarkusTest
@TestTransaction
public class MemberAddedEventPublisherTests {
    @Inject
    MemberAddedEventPublisher pub;

    @Test
    public void testConnection() throws JsonProcessingException {
        MemberAddedEvent event = new MemberAddedEvent("event1", "memberid", "name", "h1", LocalDateTime.now());
        pub.publishMessage(MemberAddedSerializer.parse(event));
    }
}
