package de.stromentlastung.unternehmen.register;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import de.stromentlastung.unternehmen.common.CurrentUser;
import de.stromentlastung.unternehmen.config.SecurityConfig;
import de.stromentlastung.unternehmen.register.dto.UnternehmenResponse;

@WebMvcTest(MeController.class)
@Import({SecurityConfig.class, CurrentUser.class})
class MeControllerTest {

    @Autowired
    MockMvc mvc;
    @MockitoBean
    UnternehmenService unternehmen;
    @MockitoBean
    ZuordnungService zuordnungen;

    @Test
    void antragsteller_sieht_das_eigene_unternehmen() throws Exception {
        when(unternehmen.eigenes()).thenReturn(new UnternehmenResponse("U-001", "Nordfeld Metallbau GmbH", "GmbH",
                "Werftstraße 12", "24103", "Kiel", "HZA-N", null));
        mvc.perform(get("/me").with(jwt().jwt(j -> j.claim("unternehmen", "U-001"))
                        .authorities(new SimpleGrantedAuthority("ROLE_ANTRAGSTELLER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kennung").value("U-001"))
                .andExpect(jsonPath("$.hauptzollamt").value("HZA-N"));
    }

    @Test
    void sachbearbeitung_hat_kein_eigenes_unternehmen() throws Exception {
        mvc.perform(get("/me").with(jwt().authorities(new SimpleGrantedAuthority("ROLE_SACHBEARBEITUNG"))))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("ZUGRIFF_VERWEIGERT"));
    }

    @Test
    void ohne_token_401() throws Exception {
        mvc.perform(get("/me")).andExpect(status().isUnauthorized());
    }
}
