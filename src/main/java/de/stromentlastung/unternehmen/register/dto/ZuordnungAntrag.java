package de.stromentlastung.unternehmen.register.dto;

import de.stromentlastung.unternehmen.register.MassgebenderZeitraum;
import de.stromentlastung.unternehmen.register.Schwerpunktkriterium;
import de.stromentlastung.unternehmen.register.Wirtschaftszweig;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ZuordnungAntrag(@Min(2000) @Max(2100) int entnahmejahr, @NotNull Wirtschaftszweig beantragterAbschnitt,
                              @NotNull MassgebenderZeitraum massgebenderZeitraum, Schwerpunktkriterium schwerpunktkriterium,
                              @Size(max = 2000) String taetigkeitsbeschreibung) {
}
