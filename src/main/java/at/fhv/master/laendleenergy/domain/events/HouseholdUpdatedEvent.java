package at.fhv.master.laendleenergy.domain.events;

import at.fhv.master.laendleenergy.domain.Household;
import java.time.LocalDateTime;

public class HouseholdUpdatedEvent {
    private String eventId;
    private Household household;
    private LocalDateTime timestamp;

    public HouseholdUpdatedEvent(String eventId, Household household, LocalDateTime timestamp) {
        this.eventId = eventId;
        this.household = household;
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }
}
