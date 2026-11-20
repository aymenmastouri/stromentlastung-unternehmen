package de.stromentlastung.unternehmen.register;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Die Zuordnung eines Unternehmens zu einem Abschnitt der WZ 2003 für ein
 * Entnahmejahr. Das Unternehmen beantragt sie mit maßgebendem Zeitraum und
 * Schwerpunktkriterium, das Hauptzollamt entscheidet (§ 15 StromStV, R-02).
 */
@Entity
@Table(name = "zuordnung")
public class Zuordnung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "unternehmen_kennung", nullable = false)
    private String unternehmenKennung;
    @Column(nullable = false)
    private int entnahmejahr;
    @Enumerated(EnumType.STRING)
    @Column(name = "beantragter_abschnitt", nullable = false)
    private Wirtschaftszweig beantragterAbschnitt;
    @Enumerated(EnumType.STRING)
    @Column(name = "massgebender_zeitraum", nullable = false)
    private MassgebenderZeitraum massgebenderZeitraum;
    @Enumerated(EnumType.STRING)
    private Schwerpunktkriterium schwerpunktkriterium;
    private String taetigkeitsbeschreibung;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ZuordnungZustand zustand;
    @Enumerated(EnumType.STRING)
    @Column(name = "entschiedener_abschnitt")
    private Wirtschaftszweig entschiedenerAbschnitt;
    private String begruendung;
    @Column(name = "entschieden_von")
    private String entschiedenVon;
    @Column(name = "entschieden_am")
    private Instant entschiedenAm;
    @Column(name = "beantragt_am", nullable = false)
    private Instant beantragtAm;

    protected Zuordnung() {
    }

    public static Zuordnung beantragt(String unternehmenKennung, int entnahmejahr, Wirtschaftszweig abschnitt,
                                      MassgebenderZeitraum zeitraum, Schwerpunktkriterium kriterium,
                                      String beschreibung, Instant jetzt) {
        Zuordnung z = new Zuordnung();
        z.unternehmenKennung = unternehmenKennung;
        z.entnahmejahr = entnahmejahr;
        z.erneuere(abschnitt, zeitraum, kriterium, beschreibung, jetzt);
        return z;
    }

    public void erneuere(Wirtschaftszweig abschnitt, MassgebenderZeitraum zeitraum, Schwerpunktkriterium kriterium,
                         String beschreibung, Instant jetzt) {
        this.beantragterAbschnitt = abschnitt;
        this.massgebenderZeitraum = zeitraum;
        this.schwerpunktkriterium = kriterium;
        this.taetigkeitsbeschreibung = beschreibung;
        this.zustand = ZuordnungZustand.BEANTRAGT;
        this.entschiedenerAbschnitt = null;
        this.begruendung = null;
        this.entschiedenVon = null;
        this.entschiedenAm = null;
        this.beantragtAm = jetzt;
    }

    public void entscheide(boolean bestaetigt, Wirtschaftszweig abschnitt, String begruendung, String von, Instant jetzt) {
        this.zustand = bestaetigt ? ZuordnungZustand.BESTAETIGT : ZuordnungZustand.ABGELEHNT;
        this.entschiedenerAbschnitt = abschnitt;
        this.begruendung = begruendung;
        this.entschiedenVon = von;
        this.entschiedenAm = jetzt;
    }

    public Long getId() { return id; }
    public String getUnternehmenKennung() { return unternehmenKennung; }
    public int getEntnahmejahr() { return entnahmejahr; }
    public Wirtschaftszweig getBeantragterAbschnitt() { return beantragterAbschnitt; }
    public MassgebenderZeitraum getMassgebenderZeitraum() { return massgebenderZeitraum; }
    public Schwerpunktkriterium getSchwerpunktkriterium() { return schwerpunktkriterium; }
    public String getTaetigkeitsbeschreibung() { return taetigkeitsbeschreibung; }
    public ZuordnungZustand getZustand() { return zustand; }
    public Wirtschaftszweig getEntschiedenerAbschnitt() { return entschiedenerAbschnitt; }
    public String getBegruendung() { return begruendung; }
    public String getEntschiedenVon() { return entschiedenVon; }
    public Instant getEntschiedenAm() { return entschiedenAm; }
    public Instant getBeantragtAm() { return beantragtAm; }

    /** Begünstigt ist, was das Hauptzollamt bestätigt hat — nicht, was beantragt wurde. */
    public boolean beguenstigt() {
        return zustand == ZuordnungZustand.BESTAETIGT && entschiedenerAbschnitt != null && entschiedenerAbschnitt.beguenstigt();
    }
}
