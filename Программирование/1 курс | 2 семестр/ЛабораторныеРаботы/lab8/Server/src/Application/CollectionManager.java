package Application;

import Dependency.*;
import NetInteraction.DatabaseManager;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Класс для работы с коллекцией TreeMap-...
 */
public class CollectionManager {
    private static Long maxId = 0l;
    private Date initDate;
    private TreeMap<Integer, Worker> workerList = new TreeMap<>();
    private String savePath = "Server/file.txt";
    private ObjectCheckerForServer checker = new ObjectCheckerForServer();
    private DatabaseManager dataBaseManager;
    private ReadWriteLock locker = new ReentrantReadWriteLock();
    Lock readLock =locker.readLock();
    Lock writeLock = locker.writeLock();

    public CollectionManager(DatabaseManager dataBaseManager) throws IOException {
       this.dataBaseManager = dataBaseManager;
    }

    public TreeMap<Integer, Worker> getWorkerList() {
        return workerList;
    }

    /**
     * Конструктор,в котором создается объект document "для его парсинга по дереву"(DOM)
     */


    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String loadDataBase() throws SQLException {
        String message;
        ResultSet rs = dataBaseManager.getWorkers();
        while (rs.next()) {
            Worker bufferWorker = new Worker();
            if(rs.getInt("id")>maxId) {
                maxId = (long) rs.getInt("id");
            }
            bufferWorker.setId(Long.valueOf(rs.getInt("id")));
            bufferWorker.setName(rs.getString("name"));
            bufferWorker.setSalary(rs.getInt("salary"));
            bufferWorker.setupCoordinates(rs.getInt("coordinates_x"), rs.getDouble("coordinates_y"));
            bufferWorker.setPosition(Position.valueOf(rs.getString("position")));
            bufferWorker.setStatus(Status.valueOf(rs.getString("status")));
            LocalDateTime birthday = LocalDateTime.parse(rs.getString("birthday"));
            bufferWorker.setupPersonInfo(birthday, (float) rs.getDouble("height"), rs.getString("pasportid"));
            LocalDateTime creationDate = LocalDateTime.parse(rs.getString("creationdate"));
            bufferWorker.setCreationDate(creationDate);
            bufferWorker.setAuthor(rs.getString("author"));
            workerList.put(rs.getInt("id"), bufferWorker);
        }
        initDate = new Date();
        if (checkMapOnEmpty()) {
            message = "База данных пуста, объекты в коллекцию загружены не были.";
        } else {
            message = "Кол-во объектов, которое было загружено из базы данных в коллекцию: " + workerList.size() + "!";
        }
        return message;
    }

    /**
     * Выводит информацию о коллекции
     */
    public String getInfoOfCollecttion() {
        readLock.lock();
        String info = "Тип коллекции: " + workerList.getClass() +
                "\nДата инициализации: " + initDate +
                "\nКоличество элементов: " + workerList.size();
        readLock.unlock();
        return info;
    }

    /**
     * Выводит информацию о объектах коллекции
     */
    public ArrayList<String> show() {
        synchronized (new Object()){

        }
        readLock.lock();
        ArrayList<String> strings = new ArrayList<String>();
        workerList.forEach((key, value) -> strings.add("Key: " + key + ". Value: " + value));
        readLock.unlock();
        return strings;
    }


    /**
     * Очищает коллекцию
     */
    public String clear(Command user) throws SQLException {
        writeLock.lock();
        if (dataBaseManager.clearByAuthor(user)) {
            int before = workerList.size();
            workerList.clear();
            loadDataBase();
            return "Из коллекции удалено " + (before - workerList.size()) + " объектов, author: " + user.getLogin();
        }
        writeLock.unlock();
        return "Вы ничего не добовляли в базу данных, коллекция не очищена.";
    }

    public DatabaseManager getDataBaseManager() {
        return dataBaseManager;
    }

    /**
     * Добовляет новый объект в коллекцию, пользователь сам вводит его
     *
     * @param key ключ под которым объект добовляется  в TreeMap
     */
    public String insert(int key, Command command) {
        writeLock.lock();
        String message = "Неверные данные, сотрудник не внесен в базу данных.";
        Worker worker = new Worker();
        worker.setName(command.getName());
        worker.setPosition(command.getPosition());
        worker.setStatus(command.getStatus());
        worker.setSalary(command.getSalary());
        worker.setupCoordinates(command.getX(), command.getY());
        worker.setupPersonInfo(command.getBirthday(), command.getHeight(), command.getPassportID());
        worker.setAuthor(command.getLogin());
        LocalDateTime date = LocalDateTime.now();
        worker.setCreationDate(date);
        if (key > maxId) {
            maxId++;
            worker.setId(maxId);
            if (checker.checkWorker(worker, workerList)) {
                dataBaseManager.insertWorker(worker, command);
                workerList.put(Math.toIntExact(maxId), worker);
                message = "Данные о сотруднике внесены успешно, id введеное вами больше максимального. Присвоенное id: " + maxId;
            }
        } else {
            worker.setId((long) key);
            if (checker.checkWorker(worker, workerList)) {
                dataBaseManager.insertWorkerLowerMaxId(worker, command);
                workerList.put(key, worker);
                message = "Данные о сотруднике внесены успешно.";
            }
        }
        writeLock.unlock();
        return message;
    }

    /**
     * Добовляет новый объект в коллекцию, информация о нем читается из скрипта
     *
     * @param key  ключ под которым объект добовляется  в TreeMap
     * @param info информация из скрипта
     */
    public ArrayList<String> insert(int key, List<String> info, Command command) {
        writeLock.lock();
        ArrayList<String> list = new ArrayList<>();
        LocalDateTime birthday = null;
        float height = 0;
        String passportID = null;
        Integer x = null;
        Double y = null;
        Worker worker = new Worker();
        String[] values = {"имя", "координату x", "координату y", "заработную плату", "день рождения, исапользуя формат(MMMM d, yyyy hh:mm AM/PM)", "рост", "паспортные данные", "должность", "статус"};
        for (int i = 0; i < values.length; i++) {
            switch (values[i]) {
                case "имя":
                    String scn = info.get(i);
                    String[] scnArray = scn.trim().split(" +");
                    if (scn != null && (scn.length() != 0 | scnArray[0].length() != 0)) {
                        worker.setName(scn);
                    } else {
                        list.add("Имя не может быть пустой строкой, повторите попытку.");
                    }
                    break;
                case "должность":
                    Position position = null;
                    switch (info.get(i)) {
                        case "DIRECTOR":
                            position = Position.DIRECTOR;
                            break;
                        case "LABORER":
                            position = Position.LABORER;
                            break;
                        case "HUMAN_RESOURCES":
                            position = Position.HUMAN_RESOURCES;
                            break;
                        case "HEAD_OF_DEPARTMENT":
                            position = Position.HEAD_OF_DEPARTMENT;
                            break;
                        case "MANAGER_OF_CLEANING":
                            position = Position.MANAGER_OF_CLEANING;
                            break;
                    }
                    if (position == null) {
                        list.add("Нужно было выбрать значение из списка\n" +
                                "DIRECTOR,\n" +
                                "LABORER,\n" +
                                "HUMAN_RESOURCES,\n" +
                                "HEAD_OF_DEPARTMENT,\n" +
                                "MANAGER_OF_CLEANING");
                    } else {
                        worker.setPosition(position);
                    }
                    break;
                case "статус":
                    Status status = null;
                    switch (info.get(i)) {
                        case "HIRED":
                            status = Status.HIRED;
                            break;
                        case "RECOMMENDED_FOR_PROMOTION":
                            status = Status.RECOMMENDED_FOR_PROMOTION;
                            break;
                        case "REGULAR":
                            status = Status.REGULAR;
                            break;
                        case "PROBATION":
                            status = Status.PROBATION;
                            break;
                    }
                    if (status == null) {
                        list.add("Нужно было выбрать значение из списка\n" +
                                "HIRED,\n" +
                                "RECOMMENDED_FOR_PROMOTION,\n" +
                                "REGULAR,\n" +
                                "PROBATION");
                    } else {
                        worker.setStatus(status);
                    }
                    break;
                case "координату x":
                    x = Utils.integerConverter(info.get(i));
                    if (x == null && x <= -716) {
                        list.add("Значение поля должно быть больше -716");
                    }
                    break;
                case "координату y":
                    y = Utils.doubleConverter(info.get(i));
                    if (y != null && y > 943) {
                        list.add("Максимальное значение поля: 943");
                    }
                    break;
                case "заработную плату":
                    Integer salary = null;
                    salary = Utils.integerConverter(info.get(i));
                    if (salary != null) {
                        if (salary <= 0) {
                            list.add("Зараплата должна быть больше 0");
                        } else {
                            worker.setSalary(salary);
                        }
                    } else {
                        list.add("Зарплата введена неверно");
                    }
                    break;
                case "день рождения, исапользуя формат(MMMM d, yyyy hh:mm AM/PM)":
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm a", Locale.US);
                        birthday = LocalDateTime.parse(info.get(i), formatter);
                    } catch (DateTimeParseException e) {
                        list.add("Проверьте введенную дату рождения.\nОна должна соответствовать формату: MMMM d, yyyy hh:mm AM/PM");
                    }
                    break;
                case "рост":
                    height = Utils.floatConverter(info.get(i));
                    if (height <= 0) {

                        list.add("Также рост не может быть меньше или равным 0.");
                    }
                    break;
                case "паспортные данные":
                    passportID = info.get(i);
                    String[] passport = passportID.trim().split(" +");
                    boolean isEqually = false;
                    for (Map.Entry<Integer, Worker> entry : workerList.entrySet()) {
                        if (entry.getValue().getPerson().getPassportID().equals(passportID)) {
                            isEqually = true;
                        }
                    }
                    if (passportID.length() > 44 && isEqually && (passport.length != 0 || passport[0].length() == 0)) {
                        list.add("Повторите ввод.\nДлина строки не должна быть больше 44, Строка не может быть пустой, Значение этого поля должно быть уникальным");
                    }
                    break;
            }
        }
        worker.setupCoordinates(x, y);
        worker.setupPersonInfo(birthday, height, passportID);
        worker.setAuthor(command.getLogin());
        LocalDateTime date = LocalDateTime.now();
        worker.setCreationDate(date);
        if (key > maxId) {
            maxId++;
            worker.setId(maxId);
            if (checker.checkWorker(worker, workerList)) {
                dataBaseManager.insertWorker(worker, command);
                workerList.put(Math.toIntExact(maxId), worker);
                list.add("Данные о сотруднике внесены успешно, id введеное вами больше максимального. Присвоенное id: "
                        + maxId);
            } else {
                list.add("Проверьте порядок данных в скрипте.\n" +
                        "name\nx\ny\nsalary\nbirthday\nheight\npasportID\nposition\nstatus");
            }
        } else {
            worker.setId((long) key);
            if (checker.checkWorker(worker, workerList)) {
                dataBaseManager.insertWorkerLowerMaxId(worker, command);
                workerList.put(key, worker);
                list.add("Данные о сотруднике внесены успешно.");
            } else {
                list.add("Проверьте порядок данных в скрипте.\n" +
                        "name\nx\ny\nsalary\nbirthday\nheight\npasportID\nposition\nstatus");
            }
        }
        writeLock.unlock();
        return list;
    }

    /**
     * Обновляет поле объекта из коллекции, посредством пользавательского ввода
     *
     * @param id      признак, по-которому выбирается элемент
     * @param command поле, которое хотим обновить
     */
    public String update(int id, Command command) {
        writeLock.lock();
        String element = command.getElement();
        String message = null;
        LocalDateTime birthday = null;
        float height = 0;
        String passportID = null;
        Integer x = null;
        Double y = null;
        String type = "x";
        String typeOfPerson = "birthday";
        boolean isAlive = workerList.entrySet().stream().filter((worker) -> worker.getValue().getId() == id)
                .findFirst().map(obj -> true).orElse(false);
        boolean isAll = false;
        boolean isCorrect = false;
        if (isAlive) {
            if (dataBaseManager.checkRule(command, id)) {
                Worker findWorker = workerList.get(id);
                while (!isAll) {
                    if (element.equals("position")) {
                        Position position = null;
                        switch (String.valueOf(command.getPosition())) {
                            case "DIRECTOR":
                                position = Position.DIRECTOR;
                                break;
                            case "LABORER":
                                position = Position.LABORER;
                                break;
                            case "HUMAN_RESOURCES":
                                position = Position.HUMAN_RESOURCES;
                                break;
                            case "HEAD_OF_DEPARTMENT":
                                position = Position.HEAD_OF_DEPARTMENT;
                                break;
                            case "MANAGER_OF_CLEANING":
                                position = Position.MANAGER_OF_CLEANING;
                                break;
                        }
                        if (position != null) {
                            isCorrect = true;
                            isAll = true;
                            dataBaseManager.update(id, "position", String.valueOf(position), command);
                            setupCreationDate(findWorker);
                            findWorker.setPosition(position);
                            dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate()), command);
                        } else {
                            isAll = true;
                        }
                    } else if (element.equals("status")) {
                        Status status = null;
                        switch (String.valueOf(command.getStatus())) {
                            case "HIRED":
                                status = Status.HIRED;
                                break;
                            case "RECOMMENDED_FOR_PROMOTION":
                                status = Status.RECOMMENDED_FOR_PROMOTION;
                                break;
                            case "REGULAR":
                                status = Status.REGULAR;
                                break;
                            case "PROBATION":
                                status = Status.PROBATION;
                                break;
                        }
                        if (status != null) {
                            isCorrect = true;
                            isAll = true;
                            dataBaseManager.update(id, "status", String.valueOf(status), command);
                            findWorker.setStatus(status);
                            setupCreationDate(findWorker);
                            dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate()), command);

                        } else {
                            isAll = true;
                        }

                    } else if (element.equals("coordinates")) {
                        switch (type) {
                            case "x":
                                x = command.getX();
                                if (x != null && x > -716) {
                                    type = "y";
                                } else {
                                    isAll = true;
                                }
                                break;
                            case "y":
                                y = command.getY();
                                if (y != null && y <= 943) {
                                    dataBaseManager.update(id, "coordinates_x", String.valueOf(x), command);
                                    dataBaseManager.update(id, "coordinates_y", String.valueOf(y), command);
                                    findWorker.setupCoordinates(x, y);
                                    setupCreationDate(findWorker);
                                    dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate()), command);
                                    isCorrect = true;
                                    isAll = true;
                                } else {
                                    isAll = true;
                                }
                                break;
                        }
                    } else if (element.equals("person")) {
                        switch (typeOfPerson) {
                            case "birthday":
                                birthday = command.getBirthday();
                                if (birthday != null) {
                                    typeOfPerson = "height";
                                } else {
                                    isAll = true;
                                }
                                break;
                            case "height":
                                height = command.getHeight();
                                if (height > 0) {
                                    typeOfPerson = "passportID";
                                } else {
                                    isAll = true;
                                }
                                break;
                            case "passportID":
                                passportID = command.getPassportID();
                                dataBaseManager.update(id, "birthday", String.valueOf(birthday), command);
                                dataBaseManager.update(id, "height", String.valueOf(height), command);
                                dataBaseManager.update(id, "pasportid", String.valueOf(passportID), command);
                                findWorker.setupPersonInfo(birthday, height, passportID);
                                setupCreationDate(findWorker);
                                dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate()), command);
                                isCorrect = true;
                                isAll = true;
                                break;
                        }
                    } else if (element.equals("name") | element.equals("salary")) {
                        switch (element) {
                            case "name":
                                String name = null;
                                name = command.getName();
                                String[] nameArray = name.trim().split("/s+");
                                if (name != null && (name.length() != 0 | nameArray[0].length() != 0)) {
                                    dataBaseManager.update(id, "name", String.valueOf(name), command);
                                    findWorker.setName(name);
                                    setupCreationDate(findWorker);
                                    dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate()), command);
                                    isCorrect = true;
                                    isAll = true;
                                } else {
                                    isAll = true;
                                }
                                break;
                            case "salary":
                                Integer salary = null;
                                salary = command.getSalary();
                                if (salary != null) {
                                    if (salary > 0) {
                                        dataBaseManager.update(id, "salary", String.valueOf(salary), command);
                                        findWorker.setSalary(salary);
                                        setupCreationDate(findWorker);
                                        dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate()), command);
                                        isCorrect = true;
                                        isAll = true;
                                    }
                                }
                                if (isCorrect != true) {
                                    isAll = true;
                                }
                                break;
                        }
                    }
                }
            } else {
                message = "У вас нет прав на изменение этого сотрудника!";
            }
        } else {
            message = "Cотрудник не найден в базе";
        }
        if (isCorrect) {
            message = "Данные сотрудника с id:" + command.getId() + ", успешно обновлены.";
        }
        writeLock.unlock();
        return message;
    }

    public boolean checkPassport(String passportID) {
        readLock.lock();
        String[] passport = passportID.trim().split(" +");
        boolean isCorrect = false;
        boolean isEqually = workerList.entrySet().stream().filter(entry -> entry.getValue().getPerson().getPassportID()
                .equals(passportID)).findFirst().map(o -> true).orElse(false);
        if (passportID.length() < 45 && !isEqually && (passport.length == 0 || passport[0].length() != 0)) {
            isCorrect = true;
        }
        readLock.unlock();
        return isCorrect;
    }

    /**
     * Обновляет поле объекта из коллекции, посредством чтения из скрипта
     *
     * @param id      признак, по-которому выбирается элемент
     * @param element поле, которое хотим обновить
     * @param info    информация из скрипта
     */
    public ArrayList<String> update(int id, String element, List<String> info, Command command) {
        writeLock.lock();
        ArrayList<String> list = new ArrayList<>();
        LocalDateTime birthday = null;
        Worker findWorker = null;
        float height = 0;
        String passportID = null;
        Integer x = null;
        Double y = null;
        String type = "x";
        String typeOfPerson = "birthday";
        boolean isAlive = workerList.entrySet().stream().filter((worker) -> worker.getValue().getId() == id)
                .findFirst().map(obj -> true).orElse(false);
        if (isAlive) {
            findWorker = workerList.get(id);
            if (dataBaseManager.checkRule(command, id)) {
                if (element.equals("position")) {
                    Position position = null;
                    switch (info.get(0)) {
                        case "DIRECTOR":
                            position = Position.DIRECTOR;
                            break;
                        case "LABORER":
                            position = Position.LABORER;
                            break;
                        case "HUMAN_RESOURCES":
                            position = Position.HUMAN_RESOURCES;
                            break;
                        case "HEAD_OF_DEPARTMENT":
                            position = Position.HEAD_OF_DEPARTMENT;
                            break;
                        case "MANAGER_OF_CLEANING":
                            position = Position.MANAGER_OF_CLEANING;
                            break;
                    }
                    if (position == null) {
                        list.add("Вы ввели непраильный position\n" +
                                "DIRECTOR,\n" +
                                "LABORER,\n" +
                                "HUMAN_RESOURCES,\n" +
                                "HEAD_OF_DEPARTMENT,\n" +
                                "MANAGER_OF_CLEANING");
                    } else {
                        dataBaseManager.update(id, "position", String.valueOf(position), command);
                        setupCreationDate(findWorker);
                        findWorker.setPosition(position);
                        dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate()), command);
                        list.add("Элемент position обновлен успешно");
                    }
                } else if (element.equals("status")) {
                    Status status = null;
                    switch (info.get(0)) {
                        case "HIRED":
                            status = Status.HIRED;
                            break;
                        case "RECOMMENDED_FOR_PROMOTION":
                            status = Status.RECOMMENDED_FOR_PROMOTION;
                            break;
                        case "REGULAR":
                            status = Status.REGULAR;
                            break;
                        case "PROBATION":
                            status = Status.PROBATION;
                            break;
                    }
                    if (status == null) {
                        list.add("status. Нужно было выбрать значение из списка\n" +
                                "HIRED,\n" +
                                "RECOMMENDED_FOR_PROMOTION,\n" +
                                "REGULAR,\n" +
                                "PROBATION");
                    } else {
                        dataBaseManager.update(id, "status", String.valueOf(status), command);
                        findWorker.setStatus(status);
                        setupCreationDate(findWorker);
                        dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate()), command);
                        list.add("Элемент status обновлен успешно");
                    }

                } else if (element.equals("coordinates")) {
                    switch (type) {
                        case "x":
                            x = Utils.integerConverter(info.get(0));
                            if (x != null && x > -716) {
                                type = "y";
                            } else {
                                list.add("Значение поля должно быть больше -716");
                            }
                            break;
                        case "y":
                            y = Utils.doubleConverter(info.get(1));
                            if (y != null && y <= 943) {
                                dataBaseManager.update(id, "coordinates_x", String.valueOf(x), command);
                                dataBaseManager.update(id, "coordinates_y", String.valueOf(y), command);
                                findWorker.setupCoordinates(x, y);
                                setupCreationDate(findWorker);
                                dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate())
                                        , command);
                                list.add("Элемент coordinates обновлен успешно");
                            } else {
                                list.add("Значение поля должно быть меньше, либо = 943");
                            }
                            break;
                    }
                } else if (element.equals("person")) {
                    switch (typeOfPerson) {
                        case "birthday":
                            try {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm a"
                                        , Locale.US);
                                birthday = LocalDateTime.parse(info.get(0), formatter);
                                if (birthday != null) {
                                    typeOfPerson = "height";
                                }
                            } catch (DateTimeParseException e) {
                                list.add("Проверьте введенную дату рождения.\nMMMM d, yyyy hh:mm AM/PM");
                            }
                            break;
                        case "height":
                            height = Utils.floatConverter(info.get(1));
                            if (height > 0) {
                                typeOfPerson = "passportID";
                            } else {
                                list.add("Также рост не может быть равен 0.");
                            }
                            break;
                        case "passportID":
                            passportID = info.get(2);
                            String[] passport = passportID.trim().split(" +");
                            boolean isEqually = false;
                            for (Map.Entry<Integer, Worker> entry : workerList.entrySet()) {
                                if (entry.getValue().getPerson().getPassportID().equals(passportID)) {
                                    isEqually = true;
                                }
                            }
                            if (passportID.length() < 45 && !isEqually && (passport.length == 0 || passport[0].length() != 0)) {
                                dataBaseManager.update(id, "birthday", String.valueOf(birthday), command);
                                dataBaseManager.update(id, "height", String.valueOf(height), command);
                                dataBaseManager.update(id, "pasportid", String.valueOf(passportID), command);
                                findWorker.setupPersonInfo(birthday, height, passportID);
                                setupCreationDate(findWorker);
                                dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate())
                                        , command);
                                list.add("Элемент person обновлен успешно");
                            } else {
                                list.add("Повторите ввод.\nДлина строки не должна быть больше 44, Строка не может быть пустой" +
                                        ", Значение этого поля должно быть уникальным");
                            }
                            break;
                    }
                } else if (element.equals("name") | element.equals("salary")) {
                    switch (element) {
                        case "name":
                            String name = null;
                            name = info.get(0);
                            String[] nameArray = name.trim().split(" +");
                            if (name != null && (name.length() != 0 | nameArray[0].length() != 0)) {
                                dataBaseManager.update(id, "name", String.valueOf(name), command);
                                findWorker.setName(name);
                                setupCreationDate(findWorker);
                                dataBaseManager.update(id, "creationdate", String.valueOf(findWorker.getCreationDate()), command);
                                list.add("Элемент name обновлен успешно");
                            } else {
                                list.add("Имя не может быть пустой строкой, повторите попытку.");
                            }
                            break;
                        case "salary":
                            Integer salary = null;
                            salary = Utils.integerConverter(info.get(0));
                            if (salary != null) {
                                if (salary > 0) {
                                    dataBaseManager.update(id, "salary", String.valueOf(salary), command);
                                    findWorker.setSalary(salary);
                                    setupCreationDate(findWorker);
                                    dataBaseManager.update(id, "creationdate",
                                            String.valueOf(findWorker.getCreationDate()), command);
                                    list.add("Элемент salary обновлен успешно");
                                } else {
                                    list.add("Зараплата должна быть больше 0");
                                }
                            } else {
                                list.add("Вы неправильно ввели зарплату");
                            }
                            break;
                    }
                }
            } else {
                list.add("У вас нет прав на изменение этого сотрудника!");
            }
        } else {
            list.add("Такого элемента нет. Поэтому обновить данные не получится");
        }
        writeLock.unlock();
        return list;
    }

    /**
     * Используется в update, чтобы уведомить пользователя о успешности операции
     *
     * @param element поле, обновление которого произошло
     */
    public void printInfoAboutOperation(String element) {
        readLock.lock();
        System.out.println("Данные " + element + " успешно обновлены.");
        readLock.unlock();
    }

    /**
     * Устанавливает время создания объекта класса Worker
     *
     * @param worker объект Worker
     */
    public void setupCreationDate(Worker worker) {
        LocalDateTime date = LocalDateTime.now();
        worker.setCreationDate(date);
    }

    /**
     * Размер TreeMap
     *
     * @param map TreeMap
     * @return размер TreeMap
     */
    public int checkMapSize(TreeMap map) {
        return map.size();
    }

    /**
     * Удаляет элемент коллекции по ключу
     *
     * @param key ключ
     */
    public String removeKey(int key, Command user) {
        writeLock.lock();
        String message = workerList.entrySet().stream().filter((worker) -> worker.getKey() == key)
                .findFirst().map(o -> "Элемент с ключом " + key + " успешно удалён.")
                .orElse("Нет элемента с таким ключом.");
        if (dataBaseManager.deleteWorker(key, user)) {
            workerList.remove(key);
        } else {
            message = "У вас нет прав на удаление!";
        }
        writeLock.unlock();
        return message;
    }

    /**
     * Сохранет коллекцию по пути, который введет пользователь
     */
    public void save() {
        readLock.lock();
        try {
            BufferedWriter buff = new BufferedWriter(new FileWriter(savePath));
            for (Map.Entry<Integer, Worker> entry : workerList.entrySet()) {
                String str = "Key: " + entry.getKey() + ". Value: " + entry.getValue();
                buff.write(str + "\n");
            }
            System.out.println("Коллекция сохранена в: " + savePath);
            buff.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
        } catch (IOException e) {
            System.out.println("Ошибка доступа к файлу.");
        }
        readLock.unlock();
    }

    /**
     * Сохранет коллекцию по пути, который получен из скрипта
     *
     * @param info путь
     */
    public String save(List<String> info) {
        readLock.lock();
        String message = null;
        try {
            BufferedWriter buff = new BufferedWriter(new FileWriter(info.get(0)));
            for (Map.Entry<Integer, Worker> entry : workerList.entrySet()) {
                String str = "Key: " + entry.getKey() + ". Value: " + entry.getValue();
                buff.write(str + "\n");
            }
            buff.close();
            message = "Сохранение данных прошло успешно.";
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден.");
        } catch (IOException e) {
            message = "Ошибка доступа к файлу.";
        }
        readLock.unlock();
        return message;

    }

    /**
     * Удаляет объекты Worker коллекции, зарплата которых превышает введеную
     *
     * @param salary введеная пользователем зарплата
     */
    public String removeGreater(String salary, Command user) throws SQLException {
        writeLock.lock();
        String message;
        if (!checkMapOnEmpty()) {
            int beginSize = checkMapSize(workerList);
            if (dataBaseManager.removeGreater(Integer.parseInt(salary), "salary", user)) {
                /*workerList.entrySet().removeIf(worker -> worker.getValue()
                        .compareSalaryValues(Utils.integerConverter(salary)) == 1);*/
                workerList.clear();
                loadDataBase();
                int calc = (beginSize - checkMapSize(workerList));
                message = "Из коллекции удален(о) " + calc + " элементов.";
            } else {
                message = "Нет рабочих с зарплатой выше введенной или у вас нет прав на удаление!";
            }
        } else {
            message = "Элемент не с чем сравнивать. Коллекция пуста.";
        }
        writeLock.unlock();
        return message;
    }

    /**
     * Меняет зраплату рабочего по ключу, если она больше введенной
     *
     * @param key   ключ для коллеции
     * @param value введенная зарплата
     */
    public String replaceIfGreater(int key, int value, Command user) {
        String message;
        if (dataBaseManager.replaceIfGreater(key, value, user)) {
            workerList.entrySet().stream().filter(o -> o.getKey() == key)
                    .filter(o -> o.getValue().compareSalaryValues(Utils.integerConverter(String.valueOf(value))) == -1)
                    .forEach(o -> o.getValue().setSalary(value));
            message = workerList.entrySet().stream().filter(o -> o.getKey() == key)
                    .filter(o -> o.getValue().compareSalaryValues(Utils.integerConverter(String.valueOf(value))) == 0)
                    .findFirst().map(o -> "Зарплата рабочего изменена.").orElse("Зарплата рабочего не может быть уменьшена.");
        } else {
            message = "Вы не имеете прав изменять этот объект!";
        }
        return message;
    }

    /**
     * Удаляет элементы коллекции, ключ которых больше
     *
     * @param key введенный ключ
     */
    public String removeGreaterKey(int key, Command user) throws SQLException {
        writeLock.lock();
        String message;
        if (!checkMapOnEmpty()) {
            int beginSize = checkMapSize(workerList);
            if (dataBaseManager.removeGreater(key, "id", user)) {
                //workerList.entrySet().removeIf(worker -> worker.getKey() > key);
                workerList.clear();
                loadDataBase();
                int calc = (beginSize - checkMapSize(workerList));
                message = "Из коллекции удален(о) " + calc + " элементов.";
            } else {
                message = "Нет элементов с id выше введеного вами или у вас отсутствуют права.";
            }
        } else {
            message = "Элемент не с чем сравнивать. Коллекция пуста.";
        }
        writeLock.unlock();
        return message;
    }

    /**
     * Проверяет коолекцию на пустоту
     *
     * @return true-пустая, false-есть элементы
     */
    public boolean checkMapOnEmpty() {
        return (checkMapSize(workerList) == 0);
    }

    /**
     * Выводит объект Worker коллекции, сумма координат x,y которого максимальная
     */
    public String maxByCoordinates() {
        readLock.lock();
        String message;
        if (!checkMapOnEmpty()) {
            message = workerList.entrySet().stream().max((o1, o2) ->
                    (int) Math.round((o1.getValue().getCoordinates().getX() + o1.getValue().getCoordinates().getY())
                            - (o2.getValue().getCoordinates().getX() + o2.getValue().getCoordinates().getY())))
                    .map(Object::toString).orElse("");
        } else {
            message = "Элемент не с чем сравнивать. Коллекция пуста.";
        }
        readLock.unlock();
        return message;
    }

    /**
     * Выводит элементы коллекции в порядке убывания ключа
     */
    public ArrayList<String> getDescending() {
        readLock.lock();
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Integer> list;
        if (!checkMapOnEmpty()) {
            list = (ArrayList<Integer>) workerList.entrySet().stream().map(o -> o.getKey()).collect(Collectors.toList());
            list.sort(Collections.reverseOrder());
            list.stream().map(this::apply).forEach(o -> strings.add(o));
        } else {
            strings.add("Коллекция пуста.");
        }
        readLock.unlock();
        return strings;
    }

    /**
     * Выводит поля position в порядке убывания ключей коллекции
     */
    public ArrayList<String> getFieldDescendingPosition() {
        readLock.lock();
        ArrayList<Integer> list;
        ArrayList<String> strings = new ArrayList<>();

        if (!checkMapOnEmpty()) {
            list = (ArrayList<Integer>) workerList.entrySet().stream().map(o -> o.getKey()).collect(Collectors.toList());
            list.sort(Collections.reverseOrder());
            list.stream().map(o -> o + ":" + workerList.get(o).getPosition().toString()).forEach(o -> strings.add(o));
        } else {
            strings.add("Коллекция пуста.");
        }
        readLock.unlock();
        return strings;
    }

    public String help() {
        String help = "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "insert null {element} : добавить новый элемент с заданным ключом=id\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_key null : удалить элемент из коллекции по его ключу=id\n" +
                "clear : очистить коллекцию\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого\n" +
                "remove_greater_key null : удалить из коллекции все элементы, ключ=id которых превышает заданный\n" +
                "max_by_coordinates : вывести любой объект из коллекции, значение поля coordinates которого является максимальным\n" +
                "print_descending : вывести элементы коллекции в порядке убывания\n" +
                "print_field_descending_position : вывести значения поля position всех элементов в порядке убывания\n" +
                "auth : выполнить авторизацию\n" +
                "reg : выполнить регистацию";
        return help;
    }

    private String apply(Integer o) {
        return workerList.get(o).toString();
    }
}
