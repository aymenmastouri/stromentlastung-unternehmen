package de.stromentlastung.unternehmen.register;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.stromentlastung.unternehmen.register.dto.UnternehmenResponse;
import de.stromentlastung.unternehmen.register.dto.UnternehmenUpdate;
import de.stromentlastung.unternehmen.register.dto.ZuordnungAntrag;
import de.stromentlastung.unternehmen.register.dto.ZuordnungResponse;
import jakarta.validation.Valid;

/** Das Antragsportal: das eigene Unternehmen und seine Zuordnungen. */
@RestController
@RequestMapping("/me")
@PreAuthorize("hasRole('ANTRAGSTELLER')")
public class MeController {

    private final UnternehmenService unternehmen;
    private final ZuordnungService zuordnungen;

    public MeController(UnternehmenService unternehmen, ZuordnungService zuordnungen) {
        this.unternehmen = unternehmen;
        this.zuordnungen = zuordnungen;
    }

    @GetMapping
    public UnternehmenResponse eigenes() {
        return unternehmen.eigenes();
    }

    @PutMapping
    public UnternehmenResponse aktualisiere(@Valid @RequestBody UnternehmenUpdate update) {
        return unternehmen.aktualisiereEigenes(update);
    }

    @GetMapping("/zuordnungen")
    public List<ZuordnungResponse> zuordnungen() {
        return zuordnungen.eigene();
    }

    @PostMapping("/zuordnungen")
    @ResponseStatus(HttpStatus.CREATED)
    public ZuordnungResponse beantrage(@Valid @RequestBody ZuordnungAntrag antrag) {
        return zuordnungen.beantrage(antrag);
    }
}
