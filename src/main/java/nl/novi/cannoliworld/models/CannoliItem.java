package nl.novi.cannoliworld.models;
import java.io.Serializable;
import java.util.Objects;

public class CannoliItem implements Serializable {



    private Long artikelnummer;   // verwijzing naar Cannoli.id
    private String naam;          // naam op moment van bestellen
    private double prijs;         // prijs op moment van bestellen
    private int qty;              // hoeveelheid

    public CannoliItem() {}

    public CannoliItem(Long artikelnummer, String naam, double prijs, int qty) {
        this.artikelnummer = artikelnummer;
        this.naam = naam;
        this.prijs = prijs;
        this.qty = qty;
    }

    public Long getArtikelnummer() { return artikelnummer; }
    public void setArtikelnummer(Long artikelnummer) { this.artikelnummer = artikelnummer; }

    public String getNaam() { return naam; }
    public void setNaam(String naam) { this.naam = naam; }

    public double getPrijs() { return prijs; }
    public void setPrijs(double prijs) { this.prijs = prijs; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CannoliItem)) return false;
        CannoliItem that = (CannoliItem) o;
        return Double.compare(that.prijs, prijs) == 0
                && qty == that.qty
                && Objects.equals(artikelnummer, that.artikelnummer)
                && Objects.equals(naam, that.naam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artikelnummer, naam, prijs, qty);
    }

    @Override
    public String toString() {
        return "CannoliItem{" +
                "artikelnummer=" + artikelnummer +
                ", naam='" + naam + '\'' +
                ", prijs=" + prijs +
                ", qty=" + qty +
                '}';
    }
}
