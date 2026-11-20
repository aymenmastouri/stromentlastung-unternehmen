package de.stromentlastung.unternehmen.register;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Die kleinste rechtlich selbständige Einheit; Betriebsstätten werden nicht abgebildet (V-12). */
@Entity
@Table(name = "unternehmen")
public class Unternehmen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String kennung;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String rechtsform;
    @Column(nullable = false)
    private String strasse;
    @Column(nullable = false)
    private String plz;
    @Column(nullable = false)
    private String ort;
    @Column(name = "hauptzollamt_kennung", nullable = false)
    private String hauptzollamtKennung;
    private String kontoverbindung;
    @Column(name = "angelegt_am", nullable = false)
    private Instant angelegtAm;

    protected Unternehmen() {
    }

    public Long getId() { return id; }
    public String getKennung() { return kennung; }
    public String getName() { return name; }
    public String getRechtsform() { return rechtsform; }
    public String getStrasse() { return strasse; }
    public String getPlz() { return plz; }
    public String getOrt() { return ort; }
    public String getHauptzollamtKennung() { return hauptzollamtKennung; }
    public String getKontoverbindung() { return kontoverbindung; }
    public Instant getAngelegtAm() { return angelegtAm; }

    public void aktualisiere(String name, String rechtsform, String strasse, String plz, String ort, String kontoverbindung) {
        this.name = name;
        this.rechtsform = rechtsform;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.kontoverbindung = kontoverbindung;
        this.hauptzollamtKennung = Zustaendigkeit.hauptzollamtFuer(plz);
    }
}
