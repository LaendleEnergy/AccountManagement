package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;

public class HouseholdUpdatedEvent extends Event {

    public HouseholdUpdatedEvent() {}

    public HouseholdUpdatedEvent(String eventId, String householdId, LocalDateTime timestamp) {
        super(eventId, householdId, timestamp);
    }
}
