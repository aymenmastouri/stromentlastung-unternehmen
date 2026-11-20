package de.stromentlastung.unternehmen.register.dto;

import de.stromentlastung.unternehmen.register.Wirtschaftszweig;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ZuordnungEntscheidung(boolean bestaetigt, @NotNull Wirtschaftszweig entschiedenerAbschnitt,
                                    @NotBlank @Size(max = 2000) String begruendung) {
}
