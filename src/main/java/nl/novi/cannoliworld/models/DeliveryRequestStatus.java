package nl.novi.cannoliworld.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum DeliveryRequestStatus {
    NEW,
    AVAILABLE,
    CONFIRMED,
    FINISHED;

    @JsonCreator
    public static DeliveryRequestStatus fromJson(String value) {
        if (value == null) return null;

        String v = value.trim().toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "");

        switch (v) {
            case "PENDING":
            case "AWAITING":
                return AVAILABLE;

            case "APPROVED":
            case "CONFIRM":
            case "CONFIRMING":
                return CONFIRMED;

            case "DELIVERED":
            case "SHIPPED":
            case "COMPLETED":
                return FINISHED;

            case "NEW":
            case "AVAILABLE":
            case "CONFIRMED":
            case "FINISHED":
                return DeliveryRequestStatus.valueOf(v);

            default:
                // Laat bewust een duidelijke fout ontstaan i.p.v. silently swallowen
                return DeliveryRequestStatus.valueOf(v);
        }
    }

    @JsonValue
    public String toJson() {
        // Altijd de canonical naam terug naar de frontend
        return name();
    }
}
