package at.fhv.master.laendleenergy.streams.publisher;

import io.quarkus.redis.client.RedisClientName;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import io.vertx.redis.client.Redis;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;

import java.util.function.Consumer;

@ApplicationScoped
@Startup
public class PricingPlanChangedEventPublisher extends EventPublisher {
    //private final PubSubCommands.RedisSubscriber subscriber;
    //private PubSubCommands<String> pub;

    @Inject
    RedisDataSource ds;


    public PricingPlanChangedEventPublisher(){
        //pub = ds.pubsub(String.class);
        //System.out.println();
        //subscriber = pub.subscribe("PricingPlanChangedChannel", (Consumer<String>) this);
    }

    public void publishMessage(){
        PubSubCommands<String> pub = ds.pubsub(String.class);
        pub.publish("PricingPlanChangedChannel", "Hallo, dies ist eine Nachricht");
        System.out.println();
    }

    @PostConstruct
    public void testPublishMessage(){
        System.out.println("Publishing...");
        publishMessage();
    }

}
