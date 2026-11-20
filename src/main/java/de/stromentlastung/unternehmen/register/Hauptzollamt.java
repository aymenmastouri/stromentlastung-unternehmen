package de.stromentlastung.unternehmen.register;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Eine der zwei fiktiven Dienststellen (Fachkonzept Kap. 3, V-16). */
@Entity
@Table(name = "hauptzollamt")
public class Hauptzollamt {

    @Id
    private String kennung;
    private String name;
    private String sitz;
    private String iban;

    protected Hauptzollamt() {
    }

    public String getKennung() { return kennung; }
    public String getName() { return name; }
    public String getSitz() { return sitz; }
    public String getIban() { return iban; }
}
