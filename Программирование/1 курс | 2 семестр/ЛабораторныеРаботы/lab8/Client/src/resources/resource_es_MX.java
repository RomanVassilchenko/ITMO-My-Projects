package resources;

import java.text.DateFormat;
import java.util.ListResourceBundle;
import java.util.Locale;

public class resource_es_MX extends ListResourceBundle {

    private static final Object[][] contents = {
            {"login", " Login:"},
            {"password", " Contraseña:"},
            {"auth", "Autorizar"},
            {"reg", "Regístrate"},
            {"message1","¡No está autorizado!"},
            {"message2","¡ya se ha creado un Usuario con dicho Inicio de sesión!"},
            {"hi", " Bienvenido:"},
            {"tableButton", "tabla"},
            {"visualButton", "Visualización"},
            {"commandButton", "ejecución de comandos"},
            {"changeUser", "Cambiar usuario"},
            {"back", "Back"},
            {"executeCommand", "Ejecutar comando"},
            {"sort", "Ordenar"},
            {"add", "Add"},
            {"remove", "Eliminar"},
            {"update", "Actualizar"},
            {"updateTable", "Actualizar tabla"},
            {"confirm", "Confirmar"},
            {"cancel", "Cancelar"},
            {"ejecutar", "Ejecutar"},
            {"newValue", " nuevo valor:"},
            {"insMessage1", " el Nombre no puede ser una cadena vacía."},
            {"insMessage2", "el Salario debe ser un número entero"},
            {"insMessage3","el valor del campo debe ser mayor que -716"},
            {"insMessage4", "valor máximo del campo: 943"},
            {"insMessage7","la longitud de la cadena no es mayor que 44, la cadena no está vacía, el valor es único"},
            {"insMessage8", "Compruebe el formato: MMMM d',' yyyy hh':'mm AM/PM"},
            {"insMessage9", " el Crecimiento no puede ser igual a 0."},
            {"numbMessage1", " debe ingresar un número entero."},
            {"numbMessage2", " debe ingresar un número flotante."},
            {"date", DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM
                    , new Locale("es","MX"))},
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
