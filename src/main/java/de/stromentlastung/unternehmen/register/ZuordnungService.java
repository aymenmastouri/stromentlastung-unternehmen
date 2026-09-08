package de.stromentlastung.unternehmen.register;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stromentlastung.unternehmen.common.CurrentUser;
import de.stromentlastung.unternehmen.common.NichtGefunden;
import de.stromentlastung.unternehmen.common.RegelVerletzung;
import de.stromentlastung.unternehmen.register.dto.ZuordnungAntrag;
import de.stromentlastung.unternehmen.register.dto.ZuordnungEntscheidung;
import de.stromentlastung.unternehmen.register.dto.ZuordnungResponse;

@Service
@Transactional
public class ZuordnungService {

    private final ZuordnungRepository zuordnungen;
    private final UnternehmenService unternehmen;
    private final CurrentUser user;
    private final Clock clock;

    public ZuordnungService(ZuordnungRepository zuordnungen, UnternehmenService unternehmen, CurrentUser user, Clock clock) {
        this.zuordnungen = zuordnungen;
        this.unternehmen = unternehmen;
        this.user = user;
        this.clock = clock;
    }

    /** Ein Unternehmen beantragt die Zuordnung für ein Entnahmejahr; nach einer Ablehnung darf es erneut beantragen. */
    public ZuordnungResponse beantrage(ZuordnungAntrag antrag) {
        String kennung = user.unternehmenOderVerweigert();
        Instant jetzt = Instant.now(clock);
        Zuordnung z = zuordnungen.findByUnternehmenKennungAndEntnahmejahr(kennung, antrag.entnahmejahr())
                .map(vorhanden -> {
                    switch (vorhanden.getZustand()) {
                        case BEANTRAGT -> throw new RegelVerletzung("ZUORDNUNG_BEREITS_BEANTRAGT",
                                "Zuordnung " + kennung + "/" + antrag.entnahmejahr() + " wartet auf Entscheidung");
                        case BESTAETIGT -> throw new RegelVerletzung("ZUORDNUNG_BEREITS_ENTSCHIEDEN",
                                "Zuordnung " + kennung + "/" + antrag.entnahmejahr() + " ist bestätigt");
                        case ABGELEHNT -> vorhanden.erneuere(antrag.beantragterAbschnitt(), antrag.massgebenderZeitraum(),
                                antrag.schwerpunktkriterium(), antrag.taetigkeitsbeschreibung(), jetzt);
                    }
                    return vorhanden;
                })
                .orElseGet(() -> zuordnungen.save(Zuordnung.beantragt(kennung, antrag.entnahmejahr(), antrag.beantragterAbschnitt(),
                        antrag.massgebenderZeitraum(), antrag.schwerpunktkriterium(), antrag.taetigkeitsbeschreibung(), jetzt)));
        return Mapper.zuordnung(z);
    }

    @Transactional(readOnly = true)
    public List<ZuordnungResponse> eigene() {
        return zuordnungen.findByUnternehmenKennungOrderByEntnahmejahrDesc(user.unternehmenOderVerweigert())
                .stream().map(Mapper::zuordnung).toList();
    }

    @Transactional(readOnly = true)
    public ZuordnungResponse eine(String kennung, int entnahmejahr) {
        Unternehmen u = unternehmen.lade(kennung);
        Zugriff.pruefeLesen(user, u);
        return Mapper.zuordnung(lade(kennung, entnahmejahr));
    }

    @Transactional(readOnly = true)
    public List<ZuordnungResponse> derDienststelle(ZuordnungZustand zustand) {
        return zuordnungen.findByDienststelleAndZustand(user.dienststelleOderVerweigert(), zustand)
                .stream().map(Mapper::zuordnung).toList();
    }

    /** Das Hauptzollamt entscheidet (§ 15 Abs. 1 StromStV); nur begünstigte Abschnitte können bestätigt werden. */
    public ZuordnungResponse entscheide(String kennung, int entnahmejahr, ZuordnungEntscheidung entscheidung) {
        Unternehmen u = unternehmen.lade(kennung);
        Zugriff.pruefeDienststelle(user, u);
        Zuordnung z = lade(kennung, entnahmejahr);
        if (z.getZustand() != ZuordnungZustand.BEANTRAGT) {
            throw new RegelVerletzung("ZUORDNUNG_NICHT_OFFEN", "Zuordnung " + kennung + "/" + entnahmejahr + " ist " + z.getZustand());
        }
        if (entscheidung.bestaetigt() && !entscheidung.entschiedenerAbschnitt().beguenstigt()) {
            throw new RegelVerletzung("ABSCHNITT_NICHT_BEGUENSTIGT",
                    "Abschnitt " + entscheidung.entschiedenerAbschnitt() + " ist weder Produzierendes Gewerbe noch Land- und Forstwirtschaft");
        }
        z.entscheide(entscheidung.bestaetigt(), entscheidung.entschiedenerAbschnitt(), entscheidung.begruendung(),
                user.kontoname(), Instant.now(clock));
        return Mapper.zuordnung(z);
    }

    private Zuordnung lade(String kennung, int entnahmejahr) {
        return zuordnungen.findByUnternehmenKennungAndEntnahmejahr(kennung, entnahmejahr)
                .orElseThrow(() -> new NichtGefunden("Zuordnung " + kennung + "/" + entnahmejahr));
    }
}
