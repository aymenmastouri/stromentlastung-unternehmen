package de.stromentlastung.unternehmen.register.dto;

import java.time.Instant;

import de.stromentlastung.unternehmen.register.MassgebenderZeitraum;
import de.stromentlastung.unternehmen.register.Schwerpunktkriterium;
import de.stromentlastung.unternehmen.register.Wirtschaftszweig;
import de.stromentlastung.unternehmen.register.ZuordnungZustand;

public record ZuordnungResponse(String unternehmen, int entnahmejahr, Wirtschaftszweig beantragterAbschnitt,
                                MassgebenderZeitraum massgebenderZeitraum, Schwerpunktkriterium schwerpunktkriterium,
                                String taetigkeitsbeschreibung, ZuordnungZustand zustand, boolean beguenstigt,
                                Wirtschaftszweig entschiedenerAbschnitt, String begruendung, String entschiedenVon,
                                Instant entschiedenAm, Instant beantragtAm) {
}
