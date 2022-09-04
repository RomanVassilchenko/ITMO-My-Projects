package resources;

import java.text.DateFormat;
import java.util.ListResourceBundle;
import java.util.Locale;

public class    resource_ru_RU extends ListResourceBundle {

    private static final Object[][] contents = {
            {"login", "Логин:"},
            {"password", "Пароль:"},
            {"auth", "Авторизоваться"},
            {"reg", "Зарегистрироваться"},
            {"message1","Вы не авторизованы!"},
            {"message2","Пользователь с таким логином уже создан!"},
            {"message3","Ошибка в логине или пароле!"},
            {"hi","Приветствуем:"},
            {"tableButton","Таблица"},
            {"visualButton","Визуализация"},
            {"commandButton","Выполнение команд"},
            {"changeUser","Сменить пользователя"},
            {"back","Назад"},
            {"executeCommand","Выполнить команду"},
            {"sort","Сортировать"},
            {"add","Добавить"},
            {"remove","Удалить"},
            {"update","Обновить"},
            {"updateTable","Обновить таблицу"},
            {"confirm","Подтвердить"},
            {"cancel","Отменить"},
            {"execute","Выполнить"},
            {"newValue","Новое значение:"},
            {"insMessage1","Имя не может быть пустой строкой."},
            {"insMessage2","Зарплата должна быть целым числом"},
            {"insMessage3","Значение поля должно быть больше -716"},
            {"insMessage4","Максимальное значение поля: 943"},
            {"insMessage7","Длина строки не больше 44, строка не пустая, значение уникальное"},
            {"insMessage8","Проверьте формат: yyyy-MM-dd HH:mm"},
            {"insMessage9","Рост не может быть равен 0."},
            {"numbMessage1","Вы должны ввести целое число."},
            {"numbMessage2","Вы должны ввести число с плавающей точкой."},
            {"executeTitle","Терминал для ввода команд."},
            {"date", DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM
                    , new Locale("ru","RU"))},
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
