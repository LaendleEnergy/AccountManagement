package at.fhv.master.laendleenergy.application.streams.publisher;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Startup
public class MemberUpdatedEventPublisher {
    @Inject
    RedisClient redisClient;
    @ConfigProperty(name = "redis-member-updated-key")  private String KEY;

    public MemberUpdatedEventPublisher(){}

    public void publishMessage(String message) {
        redisClient.publish(KEY, message);
    }
}