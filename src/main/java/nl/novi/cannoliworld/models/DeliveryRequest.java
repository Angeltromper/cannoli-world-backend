package nl.novi.cannoliworld.models;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "delivery_requests",
        indexes = {
                @Index(name = "idx_dr_applier", columnList = "applier_id"),
                @Index(name = "idx_dr_status", columnList = "status")
        }
)
@TypeDef(name = "json", typeClass = JsonType.class)
public class DeliveryRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // JSONB: lijst van bestelregels (CannoliItem-POJO)
    @Type(type = "json")
    @Column(name = "cannoli_list", columnDefinition = "jsonb", nullable = false)
    private List<CannoliItem> cannoliList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private DeliveryRequestStatus status = DeliveryRequestStatus.NEW; // kies je gewenste default

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "applier_id", nullable = true)
    private Person applier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliverer_id")
    private Person deliverer;

    /* Getters / Setters */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<CannoliItem> getCannoliList() { return cannoliList; }
    public void setCannoliList(List<CannoliItem> cannoliList) {
        this.cannoliList = (cannoliList != null) ? cannoliList : new ArrayList<>();
    }

    public DeliveryRequestStatus getStatus() { return status; }
    public void setStatus(DeliveryRequestStatus status) { this.status = status; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Person getApplier() { return applier; }
    public void setApplier(Person applier) { this.applier = applier; }

    public Person getDeliverer() { return deliverer; }
    public void setDeliverer(Person deliverer) { this.deliverer = deliverer; }

    /* equals/hashCode op basis van id */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryRequest)) return false;
        DeliveryRequest that = (DeliveryRequest) o;
        return id != null && Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() { return 31; }
}
