package resources;

import java.text.DateFormat;
import java.util.ListResourceBundle;
import java.util.Locale;

public class resource_de_DE extends ListResourceBundle {
    private static final Object[][] contents = {
            {"login", "Anmeldung:"},
            {"password", "Passwort:"},
            {"auth", "Einloggen"},
            {"reg", "Registrieren"},
            {"message1","Sie sind nicht autorisiert!"},
            {"message2","Ein Benutzer mit einem solchen Login wurde bereits erstellt!"},
            {"message3","Fehler im Login oder Passwort!"},
            {"hi","Willkommen:"},
            {"tableButton","Tabelle"},
            {"visualButton","Rendern"},
            {"commandButton","Befehle ausführen"},
            {"changeUser","Benutzer ändern"},
            {"back","Zurück"},
            {"executeCommand","Befehl ausführen"},
            {"sort","Sortieren"},
            {"add","Hinzufügen"},
            {"remove","Entfernen"},
            {"update","Aktualisieren"},
            {"updateTable","Tabelle Aktualisieren"},
            {"confirm","Bestätigen"},
            {"cancel","Abbrechen"},
            {"execute","Ausführen"},
            {"newValue","Neuer Wert:"},
            {"insMessage1","Der Name darf keine leere Zeichenfolge sein."},
            {"insMessage2","Gehalt muss eine ganze Zahl sein"},
            {"insMessage3","Der Feldwert muss größer als -716 sein"},
            {"insMessage4","Maximaler Feldwert: 943"},
            {"insMessage7","Die Zeilenlänge ist nicht größer als 44, die Zeichenfolge ist nicht leer, der Wert ist eindeutig"},
            {"insMessage8","Überprüfen Sie das Format: yyyy-MM-dd HH:mm"},
            {"insMessage9","Das Wachstum kann nicht 0 sein."},
            {"numbMessage1","Sie müssen eine ganze Zahl eingeben."},
            {"numbMessage2","Sie müssen eine Gleitkommazahl eingeben."},
            {"executeTitle","Terminal zum Eingeben von Befehlen."},
            {"date", DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM
                    , new Locale("de","DE"))},
            {"",""},
            {"",""},
            {"",""},
            {"",""}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
