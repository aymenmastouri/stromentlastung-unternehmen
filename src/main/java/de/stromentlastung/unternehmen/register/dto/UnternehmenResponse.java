package de.stromentlastung.unternehmen.register.dto;

public record UnternehmenResponse(String kennung, String name, String rechtsform, String strasse, String plz,
                                  String ort, String hauptzollamt, String kontoverbindung) {
}
