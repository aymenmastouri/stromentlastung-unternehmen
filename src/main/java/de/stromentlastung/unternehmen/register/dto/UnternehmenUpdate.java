package de.stromentlastung.unternehmen.register.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UnternehmenUpdate(@NotBlank @Size(max = 200) String name, @NotBlank @Size(max = 40) String rechtsform,
                                @NotBlank @Size(max = 200) String strasse, @Pattern(regexp = "\\d{5}") String plz,
                                @NotBlank @Size(max = 120) String ort, @Size(max = 120) String kontoverbindung) {
}
