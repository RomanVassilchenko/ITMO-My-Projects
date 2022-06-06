package Dependency;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class Command implements Serializable {
    private static final long serialVersionUID = 932644904043041495L;
    private String userCommand = "";
    private ArrayList<String> strings;
    private TreeMap<Integer, Worker> workerList;
    private boolean isCollection;
    private int id;
    private Integer key;
    private String element;
    DataCheckerForClient checker = new DataCheckerForClient();
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
        String[] values = {"имя", "координату x", "координату y", "заработную плату", "день рождения, исапользуя формат(MMMM d, yyyy hh:mm AM/PM)", "рост", "должность", "статус"};
        for (int i = 0; i < values.length; i++) {
            boolean isCorrect = false;
            while (!isCorrect) {
                try {
                switch (values[i]) {
                    case "имя":
                        System.out.println("Введите " + values[i] + ":");
                        String scn = Utils.scanner().nextLine();
                        if (checker.checkName(scn)) {
                            name = scn;
                            isCorrect = true;
                        } else {
                            System.err.println("Имя не может быть пустой строкой, повторите попытку.");
                        }
                        break;
                    case "должность":
                        System.out.println("Введите " + values[i] + ", выбрав её в предложенном списке:");
                        System.out.println("DIRECTOR,\n" +
                                "LABORER,\n" +
                                "HUMAN_RESOURCES,\n" +
                                "HEAD_OF_DEPARTMENT,\n" +
                                "MANAGER_OF_CLEANING");
                        switch (Utils.scanner().nextLine()) {
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
                            System.err.println("Нужно было выбрать значение из списка");
                        } else {
                            isCorrect = true;
                        }
                        break;
                    case "статус":
                        System.out.println("Введите " + values[i] + ", выбрав его в предложенном списке:");
                        System.out.println("HIRED,\n" +
                                "RECOMMENDED_FOR_PROMOTION,\n" +
                                "REGULAR,\n" +
                                "PROBATION");
                        switch (Utils.scanner().nextLine()) {
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
                        if (checker.checkStatus(status)) {
                            isCorrect = true;
                        }
                        break;
                    case "координату x":
                        System.out.println("Введите " + values[i] + ":");
                        //x = Utils.integerConverter(Utils.scanner().nextLine());
                        if (checker.checkX(x)) {
                            isCorrect = true;
                        }
                        break;
                    case "координату y":
                        System.out.println("Введите " + values[i] + ":");
                     //   y = Utils.doubleConverter(Utils.scanner().nextLine());
                        if (checker.checkY(y)) {
                            isCorrect = true;
                        }
                        break;
                    case "заработную плату":
                        System.out.println("Введите " + values[i] + ":");
                       // salary = Utils.integerConverter(Utils.scanner().nextLine());
                        if (checker.checkSalary(salary)) {
                            isCorrect = true;
                        }
                        break;
                    case "день рождения, исапользуя формат(MMMM d, yyyy hh:mm AM/PM)":
                        System.out.println("Введите " + values[i] + ":");
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm a", Locale.US);
                            birthday = LocalDateTime.parse(Utils.scanner().nextLine(), formatter);
                            if (birthday != null) {
                                isCorrect = true;
                            }
                        } catch (DateTimeParseException e) {
                            System.err.println("Проверьте введенную дату рождения.\nОна должна соответствовать формату: MMMM d, yyyy hh:mm AM/PM");
                        }
                        break;
                    case "рост":
                        System.out.println("Введите " + values[i] + ":");
                        height = Utils.floatConverter(Utils.scanner().nextLine());
                        if (checker.checkHeight(height)) {
                            isCorrect = true;
                        }
                        break;
                }
            }catch (NoSuchElementException e){
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
                System.out.println("DIRECTOR,\n" +
                        "LABORER,\n" +
                        "HUMAN_RESOURCES,\n" +
                        "HEAD_OF_DEPARTMENT,\n" +
                        "MANAGER_OF_CLEANING");
                Position position = null;
                switch (Utils.scanner().nextLine()) {
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
                    System.err.println("Вы ввели неправильный position");
                } else {
                    isCorrect = true;
                    this.position = position;
                }
            } else if (element.equals("status")) {
                System.out.println("Введите статус, выбрав его в предложенном списке:");
                System.out.println("HIRED,\n" +
                        "RECOMMENDED_FOR_PROMOTION,\n" +
                        "REGULAR,\n" +
                        "PROBATION");
                Status status = null;
                switch (Utils.scanner().nextLine()) {
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
                    System.err.println("status. Нужно было выбрать значение из списка");
                } else {
                    isCorrect = true;
                    this.status = status;
                }
            } else if (element.equals("coordinates")) {
                switch (type) {
                    case "x":
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
                        break;
                    case "y":
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
                        break;
                }
            } else if (element.equals("person")) {
                switch (typeOfPerson) {
                    case "birthday":
                        System.out.println("Введите дату рождения, используя формат(MMMM d, yyyy hh:mm AM/PM):");
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm a", Locale.US);
                            birthday = LocalDateTime.parse(Utils.scanner().nextLine(), formatter);
                            if (birthday != null) {
                                typeOfPerson = "height";
                            }
                        } catch (DateTimeParseException e) {
                            System.err.println("Проверьте введенную дату рождения.\nMMMM d, yyyy hh:mm AM/PM");
                        }
                        break;
                    case "height":
                        System.out.println("Введите рост:");
                        height = Utils.floatConverter(Utils.scanner().nextLine());
                        if (height > 0) {
                            isCorrect = true;
                        } else {
                            System.err.println("Также рост не может быть равен 0.");
                        }
                        break;
                }
            } else if (element.equals("name") | element.equals("salary")) {
                switch (element) {
                    case "name":
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
                        break;
                    case "salary":
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
                        break;
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