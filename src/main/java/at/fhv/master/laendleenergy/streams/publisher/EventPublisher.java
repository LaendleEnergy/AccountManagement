package at.fhv.master.laendleenergy.streams.publisher;

public abstract class EventPublisher {
    protected final static int INITIAL_DELAY_MS = 3000;
    protected final static int FIXED_DELAY_MS = 2000;

    protected String REDIS_ADDRESS = "localhost";

    protected Integer REDIS_PORT = 6379;




}
