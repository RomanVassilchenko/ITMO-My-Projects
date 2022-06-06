package resources;

import java.text.DateFormat;
import java.util.ListResourceBundle;
import java.util.Locale;

public class resource_lt_LT extends ListResourceBundle {
    private static final Object[][] contents = {
            {"login", "Prisijungimas:"},
            {"password", "Slaptažodis:"},
            {"auth", "Авторизоваться"},
            {"reg", "Registruotis"},
            {"message1","Jūs neturite teisės!"},
            {"message2","Пользаваетль su tokiu логином jau sukurta!"},
            {"hi","Sveikiname:"},
            {"tableButton","Lentelė"},
            {"visualButton","Vizualizacija"},
            {"commandButton","Vykdyti komandas"},
            {"changeUser","Keisti vartotojo"},
            {"back","Atgal"},
            {"executeCommand","Vykdyti komandą"},
            {"sort","Rūšiuoti"},
            {"add","Pridėti"},
            {"remove","Ištrinti"},
            {"update","Atnaujinti"},
            {"updateTable","Atnaujinti lentelę"},
            {"confirm","Patvirtinti"},
            {"cancel","Atšaukti"},
            {"execute","Vykdyti"},
            {"newValue","Naują reikšmę:"},
            {"insMessage1","Pavadinimas negali būti tuščia eilutė."},
            {"insMessage2","darbo užmokestis turi būti sveikasis skaičius"},
            {"insMessage3","lauko Reikšmė turi būti didesnė nei -716"},
            {"insMessage4","Didžiausią lauko reikšmė: 943"},
            {"insMessage7","eilutės Ilgis yra ne didesnis 44, eilutė nėra tuščia, vertė unikalus"},
            {"insMessage8","Patikrinkite formatas: MMMM d',' yyyy hh':'mm, AM/PM"},
            {"insMessage9","Augimas negali būti lygi 0."},
            {"numbMessage1","Jūs turite įvesti sveikasis skaičius."},
            {"numbMessage2","Jūs turite įvesti slankiojo kablelio."},
            {"date", DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM
                    , new Locale("lt","LT"))},
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
