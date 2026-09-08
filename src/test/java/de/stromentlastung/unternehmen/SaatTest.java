package de.stromentlastung.unternehmen;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.stromentlastung.unternehmen.register.HauptzollamtRepository;
import de.stromentlastung.unternehmen.register.UnternehmenRepository;
import de.stromentlastung.unternehmen.register.ZuordnungRepository;
import de.stromentlastung.unternehmen.register.ZuordnungZustand;

/** Die Migrationen laufen gegen H2 im Speicher; die Saat erzählt dreizehn Geschichten. */
@SpringBootTest
class SaatTest {

    @Autowired
    UnternehmenRepository unternehmen;
    @Autowired
    HauptzollamtRepository hauptzollaemter;
    @Autowired
    ZuordnungRepository zuordnungen;

    @Test
    void saat_ist_vollstaendig() {
        assertThat(hauptzollaemter.count()).isEqualTo(2);
        assertThat(unternehmen.count()).isEqualTo(13);
        assertThat(unternehmen.findByKennung("U-008")).get().extracting("hauptzollamtKennung").isEqualTo("HZA-S");
        assertThat(zuordnungen.findByDienststelleAndZustand("HZA-N", ZuordnungZustand.BEANTRAGT)).hasSize(3);
        assertThat(zuordnungen.findByUnternehmenKennungAndEntnahmejahr("U-009", 2025)).get()
                .extracting("zustand").isEqualTo(ZuordnungZustand.ABGELEHNT);
    }
}
