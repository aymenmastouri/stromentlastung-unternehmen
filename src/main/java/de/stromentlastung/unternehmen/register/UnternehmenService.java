package de.stromentlastung.unternehmen.register;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stromentlastung.unternehmen.common.CurrentUser;
import de.stromentlastung.unternehmen.common.NichtGefunden;
import de.stromentlastung.unternehmen.register.dto.HauptzollamtResponse;
import de.stromentlastung.unternehmen.register.dto.UnternehmenResponse;
import de.stromentlastung.unternehmen.register.dto.UnternehmenUpdate;

@Service
@Transactional
public class UnternehmenService {

    private final UnternehmenRepository unternehmen;
    private final HauptzollamtRepository hauptzollaemter;
    private final CurrentUser user;

    public UnternehmenService(UnternehmenRepository unternehmen, HauptzollamtRepository hauptzollaemter, CurrentUser user) {
        this.unternehmen = unternehmen;
        this.hauptzollaemter = hauptzollaemter;
        this.user = user;
    }

    Unternehmen lade(String kennung) {
        return unternehmen.findByKennung(kennung).orElseThrow(() -> new NichtGefunden("Unternehmen " + kennung));
    }

    @Transactional(readOnly = true)
    public UnternehmenResponse eigenes() {
        return Mapper.unternehmen(lade(user.unternehmenOderVerweigert()));
    }

    public UnternehmenResponse aktualisiereEigenes(UnternehmenUpdate update) {
        Unternehmen u = lade(user.unternehmenOderVerweigert());
        u.aktualisiere(update.name(), update.rechtsform(), update.strasse(), update.plz(), update.ort(), update.kontoverbindung());
        return Mapper.unternehmen(u);
    }

    @Transactional(readOnly = true)
    public UnternehmenResponse eines(String kennung) {
        Unternehmen u = lade(kennung);
        Zugriff.pruefeLesen(user, u);
        return Mapper.unternehmen(u);
    }

    @Transactional(readOnly = true)
    public List<UnternehmenResponse> derDienststelle() {
        return unternehmen.findByHauptzollamtKennungOrderByName(user.dienststelleOderVerweigert())
                .stream().map(Mapper::unternehmen).toList();
    }

    @Transactional(readOnly = true)
    public List<HauptzollamtResponse> hauptzollaemter() {
        return hauptzollaemter.findAll().stream().map(Mapper::hauptzollamt).toList();
    }
}
