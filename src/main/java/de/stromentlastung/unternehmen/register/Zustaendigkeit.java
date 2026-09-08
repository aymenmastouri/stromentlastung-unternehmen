package de.stromentlastung.unternehmen.register;

import de.stromentlastung.unternehmen.common.RegelVerletzung;

/**
 * Zuständig ist die Dienststelle nach dem Sitz des Unternehmens: Postleitzahlen
 * mit erster Ziffer 0 bis 4 gehören zu Nord, 5 bis 9 zu Süd (Fachkonzept Kap. 3,
 * Festlegung; die reale Zuständigkeitsordnung wird nicht abgebildet, V-16).
 */
public final class Zustaendigkeit {

    public static final String NORD = "HZA-N";
    public static final String SUED = "HZA-S";

    private Zustaendigkeit() {
    }

    public static String hauptzollamtFuer(String plz) {
        if (plz == null || !plz.matches("\\d{5}")) {
            throw new RegelVerletzung("PLZ_UNGUELTIG", "Postleitzahl muss fünfstellig sein: " + plz);
        }
        return plz.charAt(0) <= '4' ? NORD : SUED;
    }
}
