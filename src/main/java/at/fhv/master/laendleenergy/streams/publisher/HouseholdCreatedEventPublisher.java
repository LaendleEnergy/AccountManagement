package at.fhv.master.laendleenergy.streams.publisher;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Startup
public class HouseholdCreatedEventPublisher {
    @Inject
    RedisClient redisClient;
    @ConfigProperty(name = "redis-household-created-key")  private String KEY;

    public HouseholdCreatedEventPublisher(){}

    public void publishMessage(String message) {
        redisClient.publish(KEY, message);
    }
}