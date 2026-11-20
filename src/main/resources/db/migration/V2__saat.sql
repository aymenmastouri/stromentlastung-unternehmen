-- Saatdaten-Geschichten S-01 bis S-13 (Fachkonzept Kap. 12); Schlüsselvertrag in Architektur Kap. 4.
INSERT INTO hauptzollamt (kennung, name, sitz, iban) VALUES
    ('HZA-N', 'Hauptzollamt Nord', 'Kiel', 'DE02120300000000202051'),
    ('HZA-S', 'Hauptzollamt Süd', 'Rosenheim', 'DE02500105170137075030');

INSERT INTO unternehmen (kennung, name, rechtsform, strasse, plz, ort, hauptzollamt_kennung, kontoverbindung, angelegt_am) VALUES
    ('U-001', 'Nordfeld Metallbau GmbH',      'GmbH', 'Werftstraße 12',        '24103', 'Kiel',      'HZA-N', 'DE12 2105 0170 0012 3456 78', TIMESTAMP WITH TIME ZONE '2024-01-10 09:00:00+01'),
    ('U-002', 'Hansa Bau AG',                 'AG',   'Am Sandtorkai 4',       '20457', 'Hamburg',   'HZA-N', 'DE45 2005 0550 1234 5678 90', TIMESTAMP WITH TIME ZONE '2024-01-12 09:00:00+01'),
    ('U-003', 'Küstenmühle Brot GmbH',        'GmbH', 'Mühlenweg 3',           '25746', 'Heide',     'HZA-N', 'DE77 2175 0000 0009 8765 43', TIMESTAMP WITH TIME ZONE '2023-02-01 09:00:00+01'),
    ('U-004', 'Lübecker Glaswerk GmbH',       'GmbH', 'Glashüttenstraße 8',    '23552', 'Lübeck',    'HZA-N', 'DE33 2305 0101 0000 1122 33', TIMESTAMP WITH TIME ZONE '2024-03-05 09:00:00+01'),
    ('U-005', 'Weserland Kunststoffe GmbH',   'GmbH', 'Hafenstraße 21',        '28195', 'Bremen',    'HZA-N', 'DE21 2905 0101 0001 2233 44', TIMESTAMP WITH TIME ZONE '2025-01-08 09:00:00+01'),
    ('U-006', 'Elbtal Maschinenbau GmbH',     'GmbH', 'Schlachthofstraße 15',  '21079', 'Hamburg',   'HZA-S', 'DE58 2005 0550 0011 2233 44', TIMESTAMP WITH TIME ZONE '2024-02-20 09:00:00+01'),
    ('U-007', 'Ostsee Werft GmbH',            'GmbH', 'Werftallee 1',          '18055', 'Rostock',   'HZA-N', 'DE90 1305 0000 0201 0203 04', TIMESTAMP WITH TIME ZONE '2023-11-15 09:00:00+01'),
    ('U-008', 'Alpenland Solarmodule GmbH',   'GmbH', 'Innstraße 40',          '83022', 'Rosenheim', 'HZA-S', 'DE63 7115 0000 0000 4455 66', TIMESTAMP WITH TIME ZONE '2024-06-01 09:00:00+02'),
    ('U-009', 'Hafenkontor Handel GmbH',      'GmbH', 'Brandstwiete 7',        '20095', 'Hamburg',   'HZA-N', 'DE16 2005 0550 0099 8877 66', TIMESTAMP WITH TIME ZONE '2024-09-09 09:00:00+02'),
    ('U-010', 'Marschhof Agrar KG',           'KG',   'Deichweg 2',            '25836', 'Garding',   'HZA-N', 'DE04 2175 0000 0005 5566 77', TIMESTAMP WITH TIME ZONE '2024-04-04 09:00:00+02'),
    ('U-011', 'Schwarzwald Papier GmbH',      'GmbH', 'Dreisamstraße 11',      '79098', 'Freiburg',  'HZA-S', 'DE29 6805 0101 0002 3344 55', TIMESTAMP WITH TIME ZONE '2024-05-14 09:00:00+02'),
    ('U-012', 'Dithmarscher Fischwerk GmbH',  'GmbH', 'Hafenkante 9',          '25761', 'Büsum',     'HZA-N', 'DE81 2175 0000 0007 7788 99', TIMESTAMP WITH TIME ZONE '2025-02-02 09:00:00+01'),
    ('U-013', 'Wattenmeer Ziegelei GmbH',     'GmbH', 'Ziegeleiweg 5',         '26506', 'Norden',    'HZA-N', 'DE55 2835 0000 0003 3344 55', TIMESTAMP WITH TIME ZONE '2024-08-08 09:00:00+02');

INSERT INTO zuordnung (unternehmen_kennung, entnahmejahr, beantragter_abschnitt, massgebender_zeitraum, schwerpunktkriterium, taetigkeitsbeschreibung, zustand, entschiedener_abschnitt, begruendung, entschieden_von, entschieden_am, beantragt_am) VALUES
    ('U-001', 2025, 'D', 'VORJAHR', NULL, 'Herstellung von Metallkonstruktionen und Stahlbauteilen', 'BESTAETIGT', 'D', 'Verarbeitendes Gewerbe, keine weiteren Tätigkeiten', 'Sabine Wagner', TIMESTAMP WITH TIME ZONE '2026-02-10 10:00:00+01', TIMESTAMP WITH TIME ZONE '2026-01-20 09:00:00+01'),
    ('U-002', 2025, 'F', 'VORJAHR', 'UMSATZ', 'Hoch- und Tiefbau, daneben Baustoffhandel', 'BESTAETIGT', 'F', 'Schwerpunkt Baugewerbe nach steuerbarem Umsatz', 'Murat Demir', TIMESTAMP WITH TIME ZONE '2026-02-12 10:00:00+01', TIMESTAMP WITH TIME ZONE '2026-01-22 09:00:00+01'),
    ('U-003', 2023, 'D', 'VORJAHR', NULL, 'Herstellung von Backwaren', 'BESTAETIGT', 'D', 'Verarbeitendes Gewerbe', 'Sabine Wagner', TIMESTAMP WITH TIME ZONE '2024-11-12 10:00:00+01', TIMESTAMP WITH TIME ZONE '2024-11-05 09:00:00+01'),
    ('U-004', 2024, 'D', 'VORJAHR', NULL, 'Herstellung von Flachglas', 'BESTAETIGT', 'D', 'Verarbeitendes Gewerbe', 'Murat Demir', TIMESTAMP WITH TIME ZONE '2026-02-16 10:00:00+01', TIMESTAMP WITH TIME ZONE '2026-02-10 09:00:00+01'),
    ('U-005', 2026, 'D', 'VORJAHR', NULL, 'Herstellung von Kunststoffprofilen', 'BEANTRAGT', NULL, NULL, NULL, NULL, DATEADD('DAY', -12, CURRENT_TIMESTAMP)),
    ('U-006', 2025, 'D', 'VORJAHR', 'BESCHAEFTIGTE', 'Maschinenbau, daneben Instandhaltungsdienstleistungen', 'BESTAETIGT', 'D', 'Schwerpunkt Maschinenbau nach tätigen Personen', 'Sabine Wagner', TIMESTAMP WITH TIME ZONE '2026-03-03 10:00:00+01', TIMESTAMP WITH TIME ZONE '2026-02-25 09:00:00+01'),
    ('U-007', 2024, 'D', 'VORJAHR', NULL, 'Schiffbau und Reparatur', 'BESTAETIGT', 'D', 'Verarbeitendes Gewerbe', 'Sabine Wagner', TIMESTAMP WITH TIME ZONE '2025-03-20 10:00:00+01', TIMESTAMP WITH TIME ZONE '2025-03-10 09:00:00+01'),
    ('U-008', 2025, 'D', 'VORJAHR', NULL, 'Herstellung von Photovoltaikmodulen', 'BESTAETIGT', 'D', 'Verarbeitendes Gewerbe', 'Jonas Keller', TIMESTAMP WITH TIME ZONE '2026-02-05 10:00:00+01', TIMESTAMP WITH TIME ZONE '2026-01-28 09:00:00+01'),
    ('U-009', 2025, 'D', 'VORJAHR', 'BRUTTOWERTSCHOEPFUNG', 'Großhandel mit Hafenausrüstung, daneben Fertigung von Tauwerk', 'ABGELEHNT', 'G', 'Schwerpunkt Handel: 82 Prozent der Bruttowertschöpfung entfallen auf Abschnitt G', 'Murat Demir', TIMESTAMP WITH TIME ZONE '2026-03-18 10:00:00+01', TIMESTAMP WITH TIME ZONE '2026-03-02 09:00:00+01'),
    ('U-010', 2025, 'A', 'VORJAHR', NULL, 'Ackerbau und Milchviehhaltung', 'BESTAETIGT', 'A', 'Land- und Forstwirtschaft', 'Sabine Wagner', TIMESTAMP WITH TIME ZONE '2026-02-20 10:00:00+01', TIMESTAMP WITH TIME ZONE '2026-02-11 09:00:00+01'),
    ('U-011', 2025, 'D', 'VORJAHR', NULL, 'Herstellung von Papier und Pappe', 'BESTAETIGT', 'D', 'Verarbeitendes Gewerbe', 'Jonas Keller', TIMESTAMP WITH TIME ZONE '2026-03-09 10:00:00+01', TIMESTAMP WITH TIME ZONE '2026-03-01 09:00:00+01'),
    ('U-012', 2025, 'D', 'VORJAHR', NULL, 'Fischverarbeitung', 'BEANTRAGT', NULL, NULL, NULL, NULL, DATEADD('DAY', -3, CURRENT_TIMESTAMP)),
    ('U-013', 2025, 'D', 'VORJAHR', NULL, 'Herstellung von Ziegeln', 'BEANTRAGT', NULL, NULL, NULL, NULL, DATEADD('DAY', -40, CURRENT_TIMESTAMP));
