package nl.novi.cannoliworld.models;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum DeliveryRequestStatus {
    NEW,
    PENDING,
    APPROVED,
    REJECTED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    // Maakt JSON input case-insensitive: "approved", "Approved", etc.
    @JsonCreator
    public static DeliveryRequestStatus from(String value) {
        return DeliveryRequestStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
    }
}
