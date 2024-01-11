package at.fhv.master.laendleenergy.streams.publisher;

import io.quarkus.runtime.Startup;
import io.quarkus.redis.client.RedisClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Startup
public class MemberAddedEventPublisher {
    @Inject
    RedisClient redisClient;
    @ConfigProperty(name = "redis-member-added-key")  private String KEY;

    public MemberAddedEventPublisher(){}

    public void publishMessage(String message) {
        redisClient.publish(KEY, message);
    }
}