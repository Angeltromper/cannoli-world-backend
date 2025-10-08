package nl.novi.cannoliworld.models;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static nl.novi.cannoliworld.models.DeliveryRequestStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryRequestTest {

    private CannoliItem sampleItem() {
        CannoliItem i = new CannoliItem();
        i.setArtikelnummer(1001L);
        i.setNaam("Vanille 35gr");
        i.setPrijs(0.67);
        i.setQty(3);
        return i;
    }

    @Test
    @DisplayName("Getters/setters werken en status gebruikt DeliveryRequestStatus")
    void gettersSettersWork() {
        DeliveryRequest dr = new DeliveryRequest();

        dr.setId(1L);
        dr.setComment("Opmerking");
        dr.setStatus(CONFIRMED); // top-level enum
        dr.setCannoliList(List.of(sampleItem()));

        Person applier = new Person();
        Person deliverer = new Person();
        dr.setApplier(applier);
        dr.setDeliverer(deliverer);

        assertEquals(1L, dr.getId());
        assertEquals("Opmerking", dr.getComment());
        assertEquals(CONFIRMED, dr.getStatus());
        assertNotNull(dr.getCannoliList());
        assertEquals(1, dr.getCannoliList().size());
        assertSame(applier, dr.getApplier());
        assertSame(deliverer, dr.getDeliverer());
    }

    @Test
    @DisplayName("equals/hashCode alleen op id")
    void equalsAndHashCodeOnId() {
        DeliveryRequest a = new DeliveryRequest();
        a.setId(10L);
        a.setStatus(NEW);

        DeliveryRequest b = new DeliveryRequest();
        b.setId(10L);
        b.setStatus(AVAILABLE);

        DeliveryRequest c = new DeliveryRequest();
        c.setId(11L);
        c.setStatus(NEW);

        assertEquals(a, b);              // zelfde id => gelijk
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
