package de.stromentlastung.unternehmen.register;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.stromentlastung.unternehmen.common.CurrentUser;
import de.stromentlastung.unternehmen.common.RegelVerletzung;
import de.stromentlastung.unternehmen.common.ZugriffVerweigert;
import de.stromentlastung.unternehmen.register.dto.ZuordnungAntrag;
import de.stromentlastung.unternehmen.register.dto.ZuordnungEntscheidung;

class ZuordnungServiceTest {

    private final ZuordnungRepository zuordnungen = mock(ZuordnungRepository.class);
    private final UnternehmenRepository unternehmenRepo = mock(UnternehmenRepository.class);
    private final CurrentUser user = mock(CurrentUser.class);
    private final Clock clock = Clock.fixed(Instant.parse("2026-09-08T10:00:00Z"), ZoneId.of("Europe/Berlin"));
    private ZuordnungService service;
    private Unternehmen nordfeld;

    @BeforeEach
    void setUp() {
        service = new ZuordnungService(zuordnungen, new UnternehmenService(unternehmenRepo, mock(HauptzollamtRepository.class), user), user, clock);
        nordfeld = new Unternehmen();
        nordfeld.aktualisiere("Nordfeld Metallbau GmbH", "GmbH", "Werftstraße 12", "24103", "Kiel", null);
        when(unternehmenRepo.findByKennung("U-001")).thenReturn(Optional.of(nordfeld));
        when(zuordnungen.save(any())).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void zweiter_antrag_waehrend_der_erste_wartet_ist_regelverletzung() {
        when(user.unternehmenOderVerweigert()).thenReturn("U-001");
        when(zuordnungen.findByUnternehmenKennungAndEntnahmejahr("U-001", 2025)).thenReturn(Optional.empty());
        Zuordnung erste = Zuordnung.beantragt("U-001", 2025, Wirtschaftszweig.D, MassgebenderZeitraum.VORJAHR, null, null, Instant.now(clock));
        when(zuordnungen.findByUnternehmenKennungAndEntnahmejahr("U-001", 2025)).thenReturn(Optional.of(erste));

        assertThatThrownBy(() -> service.beantrage(new ZuordnungAntrag(2025, Wirtschaftszweig.D, MassgebenderZeitraum.VORJAHR, null, null)))
                .isInstanceOf(RegelVerletzung.class).extracting("code").isEqualTo("ZUORDNUNG_BEREITS_BEANTRAGT");
    }

    @Test
    void nur_beguenstigte_abschnitte_koennen_bestaetigt_werden() {
        when(user.dienststelle()).thenReturn(Optional.of("HZA-N"));
        when(user.kontoname()).thenReturn("Sabine Wagner");
        Zuordnung offen = Zuordnung.beantragt("U-001", 2025, Wirtschaftszweig.D, MassgebenderZeitraum.VORJAHR, null, null, Instant.now(clock));
        when(zuordnungen.findByUnternehmenKennungAndEntnahmejahr(anyString(), anyInt())).thenReturn(Optional.of(offen));

        assertThatThrownBy(() -> service.entscheide("U-001", 2025, new ZuordnungEntscheidung(true, Wirtschaftszweig.G, "Handel")))
                .isInstanceOf(RegelVerletzung.class).extracting("code").isEqualTo("ABSCHNITT_NICHT_BEGUENSTIGT");

        var abgelehnt = service.entscheide("U-001", 2025, new ZuordnungEntscheidung(false, Wirtschaftszweig.G, "Schwerpunkt Handel"));
        assertThat(abgelehnt.zustand()).isEqualTo(ZuordnungZustand.ABGELEHNT);
        assertThat(abgelehnt.beguenstigt()).isFalse();
    }

    @Test
    void fremde_dienststelle_darf_nicht_entscheiden() {
        when(user.dienststelle()).thenReturn(Optional.of("HZA-S"));
        assertThatThrownBy(() -> service.entscheide("U-001", 2025, new ZuordnungEntscheidung(true, Wirtschaftszweig.D, "ok")))
                .isInstanceOf(ZugriffVerweigert.class);
    }
}
