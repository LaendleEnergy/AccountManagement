package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;

public class MemberRemovedEvent extends Event {
    private String memberId;

    public MemberRemovedEvent() {}

    public MemberRemovedEvent(String eventId, String memberId, String householdId, LocalDateTime timestamp) {
        super(eventId, householdId, timestamp);
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

}
