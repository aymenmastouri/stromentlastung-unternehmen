package de.stromentlastung.unternehmen.register;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.stromentlastung.unternehmen.register.dto.HauptzollamtResponse;
import de.stromentlastung.unternehmen.register.dto.UnternehmenResponse;
import de.stromentlastung.unternehmen.register.dto.ZuordnungEntscheidung;
import de.stromentlastung.unternehmen.register.dto.ZuordnungResponse;
import jakarta.validation.Valid;

/** Die Fachanwendung: Unternehmen und Zuordnungen der eigenen Dienststelle; Einzelabruf auch für Zulieferer. */
@RestController
public class UnternehmenController {

    private final UnternehmenService unternehmen;
    private final ZuordnungService zuordnungen;

    public UnternehmenController(UnternehmenService unternehmen, ZuordnungService zuordnungen) {
        this.unternehmen = unternehmen;
        this.zuordnungen = zuordnungen;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SACHBEARBEITUNG', 'ZEICHNUNG', 'PRUEFDIENST')")
    public List<UnternehmenResponse> derDienststelle() {
        return unternehmen.derDienststelle();
    }

    @GetMapping("/hauptzollaemter")
    public List<HauptzollamtResponse> hauptzollaemter() {
        return unternehmen.hauptzollaemter();
    }

    @GetMapping("/zuordnungen")
    @PreAuthorize("hasAnyRole('SACHBEARBEITUNG', 'ZEICHNUNG', 'PRUEFDIENST')")
    public List<ZuordnungResponse> zuordnungenDerDienststelle(@RequestParam(defaultValue = "BEANTRAGT") ZuordnungZustand zustand) {
        return zuordnungen.derDienststelle(zustand);
    }

    @GetMapping("/{kennung}")
    public UnternehmenResponse eines(@PathVariable String kennung) {
        return unternehmen.eines(kennung);
    }

    @GetMapping("/{kennung}/zuordnungen/{entnahmejahr}")
    public ZuordnungResponse zuordnung(@PathVariable String kennung, @PathVariable int entnahmejahr) {
        return zuordnungen.eine(kennung, entnahmejahr);
    }

    @PostMapping("/{kennung}/zuordnungen/{entnahmejahr}/entscheidung")
    @PreAuthorize("hasRole('SACHBEARBEITUNG')")
    public ZuordnungResponse entscheide(@PathVariable String kennung, @PathVariable int entnahmejahr,
                                        @Valid @RequestBody ZuordnungEntscheidung entscheidung) {
        return zuordnungen.entscheide(kennung, entnahmejahr, entscheidung);
    }
}
