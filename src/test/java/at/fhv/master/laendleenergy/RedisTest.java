package at.fhv.master.laendleenergy;

import at.fhv.master.laendleenergy.streams.publisher.PricingPlanChangedEventPublisher;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;


@QuarkusTest
public class RedisTest {
    @Inject
    PricingPlanChangedEventPublisher pub;

    @Test
    public void testConnection() {
        pub.publishMessage();
    }
}
