package Dependency;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Класс, объекты которого помещаются в коллекцию
 */
public class Worker implements Serializable{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0,
    // Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer salary; //Поле может быть null, Значение поля должно быть больше 0
    private Position position; //Поле не может быть null
    private Status status; //Поле не может быть null
    private Person person; //Поле не может быть null
    private String author;
    /**
     * Меняет объект, хранящий координаты, объекта класса Worker
     * @param coordinates объект, хранящий координаты
     */

    public void setCoordinates(Coordinates coordinates) {

        this.coordinates = coordinates;
    }
    /**
     * Возврощает объект, хранящий координаты, объекта класса Worker
     * @return координаты
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Устанавливает новые координаты, меняя объект в поле coordinates
     * @param x новое значение координаты x
     * @param y новое значение координаты y
     */
    public void setupCoordinates(Integer x,Double y){
       coordinates = new Coordinates(x,y);
    }
    /**
     * Статический класс, объекты которого хранят координаты
     */
    public static class Coordinates implements Serializable {
        public Coordinates(Integer x,Double y){
            this.x=x;
            this.y=y;
        }
        private Integer x; //Значение поля должно быть больше -716, Поле не может быть null
        private Double y; //Максимальное значение поля: 943, Поле не может быть null

        /**
         * Получает координату x
         * @return x
         */
        public Integer getX() {
            return x;
        }

        /**
         * Меняет координату x
         * @param x координата x
         */
        public void setX(Integer x) {
            this.x = x;
        }
        /**
         * Получает координату y
         * @return y
         */
        public Double getY() {
            return y;
        }
        /**
         * Меняет координату y
         * @param y координата y
         */
        public void setY(Double y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "Coordinates{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    /**
     * Устанавливает поля для объекта статического класса Person
     * @param birthday день рождение
     * @param height рост
     * @param passportID паспортные данные
     */
    public void setupPersonInfo(LocalDateTime birthday, float height, String passportID){
        person = new Person(birthday,height,passportID);
    }

    /**
     * Его объекты хранят данные о полях birthday,height,passportID
     */
    public static class Person implements Serializable{
        /**
         * Конструктор для инициальзации полей объектов
         * @param birthday день рождения
         * @param height рост
         * @param passportID паспортные данные
         */
        public Person(LocalDateTime birthday, float height, String passportID) {
            this.birthday = birthday;
            this.height = height;
            this.passportID = passportID;
        }

        private LocalDateTime  birthday; //Поле может быть null
        private float height; //Значение поля должно быть больше 0
        private String passportID; //Длина строки не должна быть больше 44, Строка не может быть пустой,
        // Значение этого поля должно быть уникальным, Поле не может быть null

        /**
         * Возврощает поле birthday
         * @return дата и время рождения
         */
        public LocalDateTime getBirthday() {
            return birthday;
        }

        /**
         * Возврощает поле height
         * @return рост
         */
        public float getHeight() {
            return height;
        }
        /**
         * Возврощает поле passportID
         * @return паспортные данные
         */
        public String getPassportID() {
            return passportID;
        }

        /**
         * Меняет поле birthday
         * @param birthday дата рождения
         */
        public void setBirthday(LocalDateTime birthday) {
            this.birthday = birthday;
        }
        /**
         * Меняет поле height
         * @param height рост
         */
        public void setHeight(float height) {
            this.height = height;
        }
        /**
         * Меняет поле passportID
         * @param passportID паспортные данные
         */
        public void setPassportID(String passportID) {
            this.passportID = passportID;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "birthday=" + birthday +
                    ", height=" + height +
                    ", passportID='" + passportID + '\'' +
                    '}';
        }
    }

    /**
     * Возврощает поле id
     * @return id
     */
    public Long getId() {
        return id;
    }
    /**
     * Возврощает поле name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Возврощает поле creationDate
     * @return creationDate
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    /**
     * Возврощает поле salary
     * @return salary
     */
    public Integer getSalary() {
        return salary;
    }
    /**
     * Возврощает поле position
     * @return position
     */
    public Position getPosition() {
        return position;
    }
    /**
     * Возврощает поле status
     * @return status
     */
    public Status getStatus() {
        return status;
    }
    /**
     * Возврощает поле person
     * @return person
     */
    public Person getPerson() {
        return person;
    }
    /**
     * Меняет поле id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Меняет поле name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Меняет поле creationDate
     * @param creationDate creationDate
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * Меняет поле salary
     * @param salary salary
     */
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    /**
     * Меняет поле position
     * @param position position
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    /**
     * Меняет поле status
     * @param status status
     */
    public void setStatus(Status status) {
        this.status = status;
    }
    /**
     * Меняет поле person
     * @param person person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Возврощает поля объекта класса Worker
     *
     * @return информация о объекте
     */
    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +"; coordinates="+
                " coordinates_x:" + coordinates.getX() +
                ", coordinates_y:" + coordinates.getY() +
                "; creationDate=" + creationDate +
                ", salary=" + salary +
                ", position=" + position +
                ", status=" + status +
                "; person=" +" passport:" + person.getPassportID() +", birthday:" + person.getBirthday()
                +", height:" + person.getHeight() +
                "; author='" + author + '\'' +
                '}';
    }

    /**
     *  Компоратор сравнивающий зарплату сотрудника
     * @param value зарплата
     * @return результат сравнения объекта по зарплате
     */
    public int compareSalaryValues(int value) {
        return Integer.compare(this.getSalary(), value);
    }

}