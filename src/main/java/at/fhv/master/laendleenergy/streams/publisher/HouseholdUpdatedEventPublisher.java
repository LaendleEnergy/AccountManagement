package at.fhv.master.laendleenergy.streams.publisher;

import io.quarkus.runtime.Startup;
import io.quarkus.redis.client.RedisClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Startup
public class HouseholdUpdatedEventPublisher {
    @Inject
    RedisClient redisClient;

    public HouseholdUpdatedEventPublisher(){}

    public void publishMessage(String message) {
        redisClient.publish("HouseholdUpdatedEvent", message);
    }
}