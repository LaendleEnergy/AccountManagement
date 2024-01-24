package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;

public class MemberAddedEvent extends Event {
    private String memberId;
    private String name;

    public MemberAddedEvent() {}

    public MemberAddedEvent(String eventId, String memberId, String name, String householdId, LocalDateTime timestamp) {
        super(eventId, householdId, timestamp);
        this.memberId = memberId;
        this.name = name;
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
}
