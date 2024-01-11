package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;

public class MemberAddedEvent {
    private String eventId;
    private String memberId;
    private String name;
    private String householdId;
    private LocalDateTime timestamp;

    public MemberAddedEvent() {}

    public MemberAddedEvent(String eventId, String memberId, String name, String householdId, LocalDateTime timestamp) {
        this.eventId = eventId;
        this.memberId = memberId;
        this.name = name;
        this.householdId = householdId;
        this.timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
