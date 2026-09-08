package de.stromentlastung.unternehmen.register;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import de.stromentlastung.unternehmen.common.RegelVerletzung;

class ZustaendigkeitTest {

    @Test
    void erste_ziffer_bis_vier_ist_nord_ab_fuenf_sued() {
        assertThat(Zustaendigkeit.hauptzollamtFuer("24103")).isEqualTo("HZA-N");
        assertThat(Zustaendigkeit.hauptzollamtFuer("49999")).isEqualTo("HZA-N");
        assertThat(Zustaendigkeit.hauptzollamtFuer("50000")).isEqualTo("HZA-S");
        assertThat(Zustaendigkeit.hauptzollamtFuer("83022")).isEqualTo("HZA-S");
    }

    @Test
    void ungueltige_postleitzahl_ist_regelverletzung() {
        assertThatThrownBy(() -> Zustaendigkeit.hauptzollamtFuer("2410"))
                .isInstanceOf(RegelVerletzung.class)
                .extracting("code").isEqualTo("PLZ_UNGUELTIG");
    }
}
