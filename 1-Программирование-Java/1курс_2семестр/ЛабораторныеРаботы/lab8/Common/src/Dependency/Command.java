package Dependency;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 932644904043041495L;
    private String userCommand = "";
    private ArrayList<String> strings;
    private TreeMap<Integer, Worker> workerList;
    private boolean isCollection;
    private int id;
    private Integer key;
    private String element;
    final DataCheckerForClient checker = new DataCheckerForClient();
    String login;
    String password;

    public void setKey(Integer key) {
        this.key = key;
    }

    public Command(String userCommand, String login, String password) {
        this.userCommand = userCommand;
        this.login = login;
        this.password = password;
        isCollection = false;
    }
    public Command(String userCommand) {
        this.userCommand = userCommand;
        isCollection = false;
    }
    public Command(String userCommand,TreeMap<Integer, Worker> workerList) {
        this.userCommand = userCommand;
        this.workerList = workerList;
    }
    public Command(String userCommand, Integer key,String login, String password) {
        this.userCommand = userCommand;
        this.key = key;
        this.login = login;
        this.password = password;
        creatParametersForWorker();
        isCollection = false;
    }

    public Command(String userCommand, int id, String element,String login, String password) {
        this.userCommand = userCommand;
        this.id = id;
        this.element = element;
        this.login = login;
        this.password = password;
        createParameterForUpdate();
        isCollection = false;
    }

    public Command(ArrayList<String> strings,String login, String password) {
        this.strings = strings;
        this.login = login;
        this.password = password;
        isCollection = true;
    }

    public Command(ArrayList<String> strings) {
        this.strings = strings;
        isCollection = true;
    }

    public String getElement() {
        return element;
    }

    private float height = 0;
    private String passportID = null;
    private Integer x = null;
    private Double y = null;
    private Position position = null;
    private Status status = null;
    private Integer salary = null;
    private LocalDateTime birthday;
    private String name;

    public TreeMap<Integer, Worker> getWorkerList() {
        return workerList;
    }

    public void creatParametersForWorker() {
        String[] values = {"имя", "координату x", "координату y", "заработную плату", "день рождения, исапользуя формат(yyyy-MM-dd HH:mm)", "рост", "должность", "статус"};
        for (String value : values) {
            boolean isCorrect = false;
            while (!isCorrect) {
                try {
                    switch (value) {
                        case "имя" -> {
                            System.out.println("Введите " + value + ":");
                            String scn = Utils.scanner().nextLine();
                            if (checker.checkName(scn)) {
                                name = scn;
                                isCorrect = true;
                            } else {
                                System.err.println("Имя не может быть пустой строкой, повторите попытку.");
                            }
                        }
                        case "должность" -> {
                            System.out.println("Введите " + value + ", выбрав её в предложенном списке:");
                            System.out.println("""
                                    DIRECTOR,
                                    LABORER,
                                    HUMAN_RESOURCES,
                                    HEAD_OF_DEPARTMENT,
                                    MANAGER_OF_CLEANING""");
                            switch (Utils.scanner().nextLine()) {
                                case "DIRECTOR" -> position = Position.DIRECTOR;
                                case "LABORER" -> position = Position.LABORER;
                                case "HUMAN_RESOURCES" -> position = Position.HUMAN_RESOURCES;
                                case "HEAD_OF_DEPARTMENT" -> position = Position.HEAD_OF_DEPARTMENT;
                                case "MANAGER_OF_CLEANING" -> position = Position.MANAGER_OF_CLEANING;
                            }
                            if (position == null) {
                                System.err.println("Нужно было выбрать значение из списка");
                            } else {
                                isCorrect = true;
                            }
                        }
                        case "статус" -> {
                            System.out.println("Введите " + value + ", выбрав его в предложенном списке:");
                            System.out.println("""
                                    HIRED,
                                    RECOMMENDED_FOR_PROMOTION,
                                    REGULAR,
                                    PROBATION""");
                            switch (Utils.scanner().nextLine()) {
                                case "HIRED" -> status = Status.HIRED;
                                case "RECOMMENDED_FOR_PROMOTION" -> status = Status.RECOMMENDED_FOR_PROMOTION;
                                case "REGULAR" -> status = Status.REGULAR;
                                case "PROBATION" -> status = Status.PROBATION;
                            }
                            if (checker.checkStatus(status)) {
                                isCorrect = true;
                            }
                        }
                        case "координату x" -> {
                            System.out.println("Введите " + value + ":");
                            //x = Utils.integerConverter(Utils.scanner().nextLine());
                            if (checker.checkX(x)) {
                                isCorrect = true;
                            }
                        }
                        case "координату y" -> {
                            System.out.println("Введите " + value + ":");
                            //   y = Utils.doubleConverter(Utils.scanner().nextLine());
                            if (checker.checkY(y)) {
                                isCorrect = true;
                            }
                        }
                        case "заработную плату" -> {
                            System.out.println("Введите " + value + ":");
                            // salary = Utils.integerConverter(Utils.scanner().nextLine());
                            if (checker.checkSalary(salary)) {
                                isCorrect = true;
                            }
                        }
                        case "день рождения, исапользуя формат(yyyy-MM-dd HH:mm)" -> {
                            System.out.println("Введите " + value + ":");
                            try {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US);
                                birthday = LocalDateTime.parse(Utils.scanner().nextLine(), formatter);
                                if (birthday != null) {
                                    isCorrect = true;
                                }
                            } catch (DateTimeParseException e) {
                                System.err.println("Проверьте введенную дату рождения.\nОна должна соответствовать формату: yyyy-MM-dd HH:mm");
                            }
                        }
                        case "рост" -> {
                            System.out.println("Введите " + value + ":");
                            height = Utils.floatConverter(Utils.scanner().nextLine());
                            if (checker.checkHeight(height)) {
                                isCorrect = true;
                            }
                        }
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Вы вышли из консольного приложения.");
                    System.exit(0);
                }
            }
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void createParameterForUpdate() {
        String type = "x";
        String typeOfPerson = "birthday";
        boolean isCorrect = false;
        while (!isCorrect) {
            try{
            if (element.equals("position")) {
                System.out.println("Введите должность, выбрав её в предложенном списке:");
                System.out.println("""
                        DIRECTOR,
                        LABORER,
                        HUMAN_RESOURCES,
                        HEAD_OF_DEPARTMENT,
                        MANAGER_OF_CLEANING""");
                Position position = switch (Utils.scanner().nextLine()) {
                    case "DIRECTOR" -> Position.DIRECTOR;
                    case "LABORER" -> Position.LABORER;
                    case "HUMAN_RESOURCES" -> Position.HUMAN_RESOURCES;
                    case "HEAD_OF_DEPARTMENT" -> Position.HEAD_OF_DEPARTMENT;
                    case "MANAGER_OF_CLEANING" -> Position.MANAGER_OF_CLEANING;
                    default -> null;
                };
                if (position == null) {
                    System.err.println("Вы ввели неправильный position");
                } else {
                    isCorrect = true;
                    this.position = position;
                }
            } else if (element.equals("status")) {
                System.out.println("Введите статус, выбрав его в предложенном списке:");
                System.out.println("""
                        HIRED,
                        RECOMMENDED_FOR_PROMOTION,
                        REGULAR,
                        PROBATION""");
                Status status = switch (Utils.scanner().nextLine()) {
                    case "HIRED" -> Status.HIRED;
                    case "RECOMMENDED_FOR_PROMOTION" -> Status.RECOMMENDED_FOR_PROMOTION;
                    case "REGULAR" -> Status.REGULAR;
                    case "PROBATION" -> Status.PROBATION;
                    default -> null;
                };
                if (status == null) {
                    System.err.println("status. Нужно было выбрать значение из списка");
                } else {
                    isCorrect = true;
                    this.status = status;
                }
            } else if (element.equals("coordinates")) {
                switch (type) {
                    case "x" -> {
                        try {
                            System.out.println("Введите координату x:");
                            //  x = Utils.integerConverter(Utils.scanner().nextLine());
                        } catch (NoSuchElementException e) {
                            System.out.println();

                        }
                        if (x != null && x > -716) {
                            type = "y";
                        } else {
                            System.err.println("Значение поля должно быть больше -716");
                        }
                    }
                    case "y" -> {
                        System.out.println("Введите координату y:");
                        //   y = Utils.doubleConverter(Utils.scanner().nextLine());
                        if (y != null && y <= 943) {
                            this.x = x;
                            this.y = y;
                            type = "y";
                            isCorrect = true;
                        } else {
                            System.err.println("Значение поля должно быть меньше, либо = 943");
                        }
                    }
                }
            } else if (element.equals("person")) {
                switch (typeOfPerson) {
                    case "birthday" -> {
                        System.out.println("Введите дату рождения, используя формат(yyyy-MM-dd HH:mm):");
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US);
                            birthday = LocalDateTime.parse(Utils.scanner().nextLine(), formatter);
                            if (birthday != null) {
                                typeOfPerson = "height";
                            }
                        } catch (DateTimeParseException e) {
                            System.err.println("Проверьте введенную дату рождения.\nyyyy-MM-dd HH:mm");
                        }
                    }
                    case "height" -> {
                        System.out.println("Введите рост:");
                        height = Utils.floatConverter(Utils.scanner().nextLine());
                        if (height > 0) {
                            isCorrect = true;
                        } else {
                            System.err.println("Также рост не может быть равен 0.");
                        }
                    }
                }
            } else if (element.equals("name") | element.equals("salary")) {
                switch (element) {
                    case "name" -> {
                        System.out.println("Введите имя:");
                        String name = null;
                        name = Utils.scanner().nextLine();
                        String[] nameArray = name.trim().split("/s+");
                        if (name != null && (name.length() != 0 | nameArray[0].length() != 0)) {
                            this.name = name;
                            isCorrect = true;
                        } else {
                            System.err.println("Имя не может быть пустой строкой, повторите попытку.");
                        }
                    }
                    case "salary" -> {
                        System.out.println("Введите зарплату:");
                        Integer salary = null;
                        // salary = Utils.integerConverter(Utils.scanner().nextLine());
                        if (salary != null) {
                            if (salary > 0) {
                                this.salary = salary;
                                isCorrect = true;
                            } else {
                                System.err.println("Зараплата должна быть больше 0");
                            }
                        } else {
                            System.err.println("Вы неправильно ввели зарплату");
                        }
                    }
                }
            } else {
                isCorrect = true;
                System.err.println("Такого элемента нет");
            }
        } catch (NoSuchElementException e){
            System.out.println("Вы вышли из консольного приложения.");
            System.exit(0);
        }
        }

    }


    public boolean isCollection() {
        return isCollection;
    }

    public String getName() {
        return name;
    }

    public String getUserCommand() {
        return userCommand;
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    public int getId() {
        return id;
    }

    public Integer getKey() {
        return key;
    }

    public float getHeight() {
        return height;
    }

    public void setStrings(ArrayList<String> strings) {
        this.strings = strings;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassportID() {
        return passportID;
    }

    public Integer getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Position getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getSalary() {
        return salary;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setUserCommand(String userCommand) {
        this.userCommand = userCommand;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public void setName(String name) {
        this.name = name;
    }
}