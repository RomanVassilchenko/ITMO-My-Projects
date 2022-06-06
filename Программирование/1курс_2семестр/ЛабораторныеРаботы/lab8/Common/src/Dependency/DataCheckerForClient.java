package Dependency;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import static javax.swing.JOptionPane.showMessageDialog;

public class DataCheckerForClient implements Serializable {



    /**
     * Проверка поля name для checkWorker
     *
     * @param name проверяемый объект
     * @return true-правильное,false-неправильное
     */
    public boolean checkName(String name) {
        boolean isCorrect = false;
        if (name != null) {
            String[] nameArray = name.trim().split(" +");
            if (name.length() != 0 | nameArray[0].length() != 0) {
                isCorrect = true;
            }
        }
        return isCorrect;
    }
    public boolean checkWorker(Command command) {
        return checkName(command.getName()) && checkX(command.getX())
                && checkY(command.getY()) && checkSalary(command.getSalary())
                && checkHeight(command.getHeight()) && checkBirthday(command.getBirthday())
                && checkStatus(command.getStatus()) && checkPosition(command.getPosition());
    }
    /**
     * Проверка объекта статического класса Coordinates для checkWorker
     *
     * @param x проверяемый объект
     * @return true-правильный,false-неправильный
     */
    public boolean checkX(Integer x) {
        return x != null && x > -716;
    }

    public boolean checkY(Double y) {
        return y != null && y <= 943;
    }

    /**
     * Проверка поля salary для checkWorker
     *
     * @param salary проверяемый объект
     * @return true-правильное,false-неправильное
     */
    public boolean checkSalary(Integer salary) {
        boolean isCorrect = false;
        if (salary != null) {
            if (salary > 0) {
                isCorrect = true;
            }
        }
        return isCorrect;
    }

    /**
     * Проверка поля position для checkWorker
     *
     * @param position проверяемый объект
     * @return true-правильное,false-неправильное
     */
    public boolean checkPosition(String position) {
        boolean isCorrect = false;
        try{
            Position pos = Position.valueOf(position);
            if (position != null) {
                isCorrect = true;
            }
        } catch (Exception e){
            showMessageDialog(null, "Ошибка в вводе данных. Проверьте правильность данных!");
        }
        return isCorrect;
    }

    public boolean checkPosition(Position position) {
        return position != null;
    }



    /**
     * Проверка поля status для checkWorker
     *
     * @param status проверяемый объект
     * @return true-правильное,false-неправильное
     */
    public boolean checkStatus(Status status) {
        return status != null;
    }

    /**
     * Проверка объекта статического класса Person для checkWorker
     *
     * @param birthdayIn проверяемая строка
     * @return true-правильный,false-неправильный
     */
    public boolean checkBirthday(String birthdayIn) {
        boolean isCorrect = false;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm a", Locale.US);
            LocalDateTime birthday = LocalDateTime.parse(birthdayIn, formatter);
            if (birthday != null){
                isCorrect = true;
            }
        } catch (DateTimeParseException ignored) {
        }
        return isCorrect;
    }
    public boolean checkBirthday(LocalDateTime birthdayIn) {
        boolean isCorrect = false;
        try {
            if (birthdayIn != null){
                isCorrect = true;
            }
        } catch (DateTimeParseException ignored) {
        }
        return isCorrect;
    }
    public boolean checkHeight(float height) {
        return height > 0;
    }


}
