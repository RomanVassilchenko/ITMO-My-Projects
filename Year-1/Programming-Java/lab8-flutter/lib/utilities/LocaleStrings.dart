import 'package:get/route_manager.dart';

class LocaleStrings extends Translations{

  @override
  // TODO: implement keys
  Map<String, Map<String, String>> get keys => {
    'ru_Ru':{
      "email":"Email:",
      "login":"Логин:",
      "password":"Пароль:",
      "auth":"Авторизоваться",
      "reg":"Зарегистрироваться",
      "message1":"Вы не авторизованы!",
      "message2":"Пользователь с таким логином уже создан!",
      "message3":"Ошибка в логине или пароле!",
      "hi":"Приветствуем:",
      "tableButton":"Таблица",
      "visualButton":"Визуализация",
      "commandButton":"Выполнение команд",
      "changeUser":"Сменить пользователя",
      "back":"Назад",
      "executeCommand":"Выполнить команду",
      "sort":"Сортировать",
      "add":"Добавить",
      "remove":"Удалить",
      "update":"Обновить",
      "updateTable":"Обновить таблицу",
      "confirm":"Подтвердить",
      "cancel":"Отменить",
      "execute":"Выполнить",
      "newValue":"Новое значение:",
      "insMessage1":"Имя не может быть пустой строкой.",
      "insMessage2":"Зарплата должна быть целым числом",
      "insMessage3":"Значение поля должно быть больше -716",
      "insMessage4":"Максимальное значение поля: 943",
      "insMessage7":"Длина строки не больше 44, строка не пустая, значение уникальное",
      "insMessage8":"Проверьте формат: yyyy-MM-dd HH:mm",
      "insMessage9":"Рост не может быть равен 0.",
      "numbMessage1":"Вы должны ввести целое число.",
      "numbMessage2":"Вы должны ввести число с плавающей точкой.",
      "executeTitle":"Терминал для ввода команд."
    },
    'de_DE':{
      "email":"Email:",
      "login":"Anmeldung:",
      "password":"Passwort:",
      "auth":"Einloggen",
      "reg":"Registrieren",
      "message1":"Sie sind nicht autorisiert!",
      "message2":"Ein Benutzer mit einem solchen Login wurde bereits erstellt!",
      "message3":"Fehler im Login oder Passwort!",
      "hi":"Willkommen:",
      "tableButton":"Tabelle",
      "visualButton":"Rendern",
      "commandButton":"Befehle ausführen",
      "changeUser":"Benutzer ändern",
      "back":"Zurück",
      "executeCommand":"Befehl ausführen",
      "sort":"Sortieren",
      "add":"Hinzufügen",
      "remove":"Entfernen",
      "update":"Aktualisieren",
      "updateTable":"Tabelle Aktualisieren",
      "confirm":"Bestätigen",
      "cancel":"Abbrechen",
      "execute":"Ausführen",
      "newValue":"Neuer Wert:",
      "insMessage1":"Der Name darf keine leere Zeichenfolge sein.",
      "insMessage2":"Gehalt muss eine ganze Zahl sein",
      "insMessage3":"Der Feldwert muss größer als -716 sein",
      "insMessage4":"Maximaler Feldwert: 943",
      "insMessage7":"Die Zeilenlänge ist nicht größer als 44, die Zeichenfolge ist nicht leer, der Wert ist eindeutig",
      "insMessage8":"Überprüfen Sie das Format: yyyy-MM-dd HH:mm",
      "insMessage9":"Das Wachstum kann nicht 0 sein.",
      "numbMessage1":"Sie müssen eine ganze Zahl eingeben.",
      "numbMessage2":"Sie müssen eine Gleitkommazahl eingeben.",
      "executeTitle":"Terminal zum Eingeben von Befehlen.",
    },
    'es_MX':{
      "email":"Email:",
      "login":" Login:",
      "password":" Contraseña:",
      "auth":"Autorizar",
      "reg":"Regístrate",
      "message1":"¡No está autorizado!",
      "message2":"¡ya se ha creado un Usuario con dicho Inicio de sesión!",
      "message3":"Error en el Inicio de sesión o contraseña!",
      "hi":" Bienvenido:",
      "tableButton":"tabla",
      "visualButton":"Visualización",
      "commandButton":"ejecución de comandos",
      "changeUser":"Cambiar usuario",
      "back":"Back",
      "executeCommand":"Ejecutar comando",
      "sort":"Ordenar",
      "add":"Add",
      "remove":"Eliminar",
      "update":"Actualizar",
      "updateTable":"Actualizar tabla",
      "confirm":"Confirmar",
      "cancel":"Cancelar",
      "ejecutar":"Ejecutar",
      "newValue":" nuevo valor:",
      "insMessage1":" el Nombre no puede ser una cadena vacía.",
      "insMessage2":"el Salario debe ser un número entero",
      "insMessage3":"el valor del campo debe ser mayor que -716",
      "insMessage4":"valor máximo del campo: 943",
      "insMessage7":"la longitud de la cadena no es mayor que 44, la cadena no está vacía, el valor es único",
      "insMessage8":"Compruebe el formato: yyyy-MM-dd HH:mm",
      "insMessage9":" el Crecimiento no puede ser igual a 0.",
      "numbMessage1":" debe ingresar un número entero.",
      "numbMessage2":" debe ingresar un número flotante.",
      "executeTitle":"Terminal de entrada de comandos.",
    },
    'lt_LT':{
      "email":"Email:",
      "login":"Prisijungimas:",
      "password":"Slaptažodis:",
      "auth":"Авторизоваться",
      "reg":"Registruotis",
      "message1":"Jūs neturite teisės!",
      "message2":"Пользаваетль su tokiu логином jau sukurta!",
      "message3":"Klaida логине ar slaptažodį!",
      "hi":"Sveikiname:",
      "tableButton":"Lentelė",
      "visualButton":"Vizualizacija",
      "commandButton":"Vykdyti komandas",
      "changeUser":"Keisti vartotojo",
      "back":"Atgal",
      "executeCommand":"Vykdyti komandą",
      "sort":"Rūšiuoti",
      "add":"Pridėti",
      "remove":"Ištrinti",
      "update":"Atnaujinti",
      "updateTable":"Atnaujinti lentelę",
      "confirm":"Patvirtinti",
      "cancel":"Atšaukti",
      "execute":"Vykdyti",
      "newValue":"Naują reikšmę:",
      "insMessage1":"Pavadinimas negali būti tuščia eilutė.",
      "insMessage2":"darbo užmokestis turi būti sveikasis skaičius",
      "insMessage3":"lauko Reikšmė turi būti didesnė nei -716",
      "insMessage4":"Didžiausią lauko reikšmė: 943",
      "insMessage7":"eilutės Ilgis yra ne didesnis 44, eilutė nėra tuščia, vertė unikalus",
      "insMessage8":"Patikrinkite formatas: yyyy-MM-dd HH:mm",
      "insMessage9":"Augimas negali būti lygi 0.",
      "numbMessage1":"Jūs turite įvesti sveikasis skaičius.",
      "numbMessage2":"Jūs turite įvesti slankiojo kablelio.",
      "executeTitle":"Terminalo komandų įvedimas.",
    }
  };

}