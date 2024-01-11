package at.fhv.master.laendleenergy.streams.publisher;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Startup
public class MemberRemovedEventPublisher {
    @Inject
    RedisClient redisClient;
    @ConfigProperty(name = "redis-member-removed-key")  private String KEY;

    public MemberRemovedEventPublisher(){}

    public void publishMessage(String message) {
        redisClient.publish(KEY, message);
    }
}