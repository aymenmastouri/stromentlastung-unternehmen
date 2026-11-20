# stromentlastung-unternehmen – Unternehmensregister

Teil des Referenzverfahrens Stromentlastung (§ 9b StromStG); Fachkonzept und Architektur liegen im Repository `stromentlastung-platform`.

Stammdaten der Unternehmen, die fiktiven Dienststellen Nord und Süd mit Zuständigkeit nach Postleitzahl, und die Zuordnung eines Unternehmens zu einem Abschnitt der WZ 2003 nach § 15 StromStV (Fachkonzept R-02). Das Register wird von den anderen Diensten befragt, befragt aber niemanden.

## Technik

Spring Boot 3.4.5 auf Java 17, H2 mit Flyway (`V1__schema.sql`, `V2__saat.sql`), Resource Server gegen den Keycloak-Realm `stromentlastung`, OpenAPI unter `/api/unternehmen/v3/api-docs`. Regelverletzungen antworten mit 409 und `{"code": "<REGELCODE>", "message": "<Diagnose>"}`; die Oberfläche übersetzt den Code. Beträge in Cent, Mengen in Kilowattstunden, Datumsangaben ohne Zeitanteil.

## Bauen und starten

```bash
./mvnw test                      # Kern ohne Datenbank und Uhr, Endpunkte mit nachgestelltem JWT, Migrationen gegen H2 im Speicher
./mvnw spring-boot:run           # Port 8091, Kontextpfad /api/unternehmen, H2-Datei unter ./data
```

Das „Heute“ des Dienstes lässt sich mit `STROMENTLASTUNG_HEUTE=2026-09-08` einfrieren (Fachkonzept Kap. 10). Im Containerbetrieb zeigen `STROMENTLASTUNG_ISSUER` und `STROMENTLASTUNG_JWKS_URI` auf den öffentlichen Aussteller und den internen Schlüsselabruf.
