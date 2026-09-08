package de.stromentlastung.unternehmen.register;

/**
 * Abschnitte der Klassifikation der Wirtschaftszweige, Ausgabe 2003 (WZ 2003).
 * Begünstigt sind das Produzierende Gewerbe (Abschnitte C, D, E, F) und die
 * Land- und Forstwirtschaft (Abschnitt A oder Klasse 05.02), § 2 Nr. 3 und 5
 * StromStG. Anzeigenamen liegen in der Oberfläche; das Backend kennt nur Codes.
 */
public enum Wirtschaftszweig {
    A(true), B_05_02(true), C(true), D(true), E(true), F(true),
    G(false), H(false), I(false), J(false), K(false), SONSTIGE(false);

    private final boolean beguenstigt;

    Wirtschaftszweig(boolean beguenstigt) {
        this.beguenstigt = beguenstigt;
    }

    /** Fachkonzept R-02. */
    public boolean beguenstigt() {
        return beguenstigt;
    }
}
