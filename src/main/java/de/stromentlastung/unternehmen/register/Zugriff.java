package de.stromentlastung.unternehmen.register;

import de.stromentlastung.unternehmen.common.CurrentUser;
import de.stromentlastung.unternehmen.common.ZugriffVerweigert;

/**
 * Mandantentrennung (Fachkonzept Kap. 10): ein Unternehmen sieht nur sich
 * selbst, Beschäftigte sehen nur die Unternehmen ihrer Dienststelle.
 */
final class Zugriff {

    private Zugriff() {
    }

    static void pruefeLesen(CurrentUser user, Unternehmen u) {
        if (user.unternehmen().map(u.getKennung()::equals).orElse(false)) {
            return;
        }
        if (user.dienststelle().map(u.getHauptzollamtKennung()::equals).orElse(false)) {
            return;
        }
        throw new ZugriffVerweigert("kein Zugriff auf Unternehmen " + u.getKennung());
    }

    static void pruefeDienststelle(CurrentUser user, Unternehmen u) {
        if (!user.dienststelle().map(u.getHauptzollamtKennung()::equals).orElse(false)) {
            throw new ZugriffVerweigert("Unternehmen " + u.getKennung() + " gehört zu " + u.getHauptzollamtKennung());
        }
    }
}
