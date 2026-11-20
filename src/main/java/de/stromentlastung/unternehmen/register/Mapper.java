package de.stromentlastung.unternehmen.register;

import de.stromentlastung.unternehmen.register.dto.HauptzollamtResponse;
import de.stromentlastung.unternehmen.register.dto.UnternehmenResponse;
import de.stromentlastung.unternehmen.register.dto.ZuordnungResponse;

final class Mapper {

    private Mapper() {
    }

    static UnternehmenResponse unternehmen(Unternehmen u) {
        return new UnternehmenResponse(u.getKennung(), u.getName(), u.getRechtsform(), u.getStrasse(), u.getPlz(),
                u.getOrt(), u.getHauptzollamtKennung(), u.getKontoverbindung());
    }

    static ZuordnungResponse zuordnung(Zuordnung z) {
        return new ZuordnungResponse(z.getUnternehmenKennung(), z.getEntnahmejahr(), z.getBeantragterAbschnitt(),
                z.getMassgebenderZeitraum(), z.getSchwerpunktkriterium(), z.getTaetigkeitsbeschreibung(), z.getZustand(),
                z.beguenstigt(), z.getEntschiedenerAbschnitt(), z.getBegruendung(), z.getEntschiedenVon(),
                z.getEntschiedenAm(), z.getBeantragtAm());
    }

    static HauptzollamtResponse hauptzollamt(Hauptzollamt h) {
        return new HauptzollamtResponse(h.getKennung(), h.getName(), h.getIban(), h.getSitz());
    }
}
