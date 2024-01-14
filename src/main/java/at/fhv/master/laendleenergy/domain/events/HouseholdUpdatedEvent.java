package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;

public class HouseholdUpdatedEvent {
    private String eventId;
    private String householdId;
    private LocalDateTime timestamp;

    public HouseholdUpdatedEvent() {}

    public HouseholdUpdatedEvent(String eventId, String householdId, LocalDateTime timestamp) {
        this.eventId = eventId;
        this.householdId = householdId;
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

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }
}
