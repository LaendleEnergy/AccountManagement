package at.fhv.master.laendleenergy.domain.events;

import java.time.LocalDateTime;

public class PricingPlanChangedEvent {
    private String eventId;
    private double averagePricePerKwh;
    private String deviceId;
    private LocalDateTime timestamp;

    public PricingPlanChangedEvent(String eventId, double averagePricePerKwh, String deviceId, LocalDateTime timestamp) {
        this.eventId = eventId;
        this.averagePricePerKwh = averagePricePerKwh;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }

    public double getAveragePricePerKwh() {
        return averagePricePerKwh;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getEventId() {
        return eventId;
    }
}
