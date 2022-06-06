package Application;

import Dependency.Worker;

import java.util.Map;
import java.util.TreeMap;

public class ObjectCheckerForServer {


    /**
     * Проверка объекта на соотввествие требованиям к полям
     *
     * @param worker проверяемый, объект
     * @return true-можно, false-нельзя, добавить в коллекцию
     */

    public boolean checkWorker(Worker worker, TreeMap<Integer, Worker> workerList) {
        boolean isMatch = false;
        if (checkId(worker,workerList) && checkName(worker) && checkCoordinates(worker) && checkSalary(worker) && checkPerson(worker,workerList) && checkStatus(worker) && checkPosition(worker) && worker.getCreationDate() != null) {
            isMatch = true;
        }
        return isMatch;
    }

    /**
     * Проверка поля id для checkWorker
     *
     * @param worker проверяемый объект
     * @return true-правильное,false-неправильное
     */
    public boolean checkId(Worker worker,TreeMap<Integer, Worker> workerList) {
        boolean isEquallyID = false;
        boolean id = false;
        for (Map.Entry<Integer, Worker> entry : workerList.entrySet()) {
            if (entry.getValue().getId().equals(worker.getId())) {
                isEquallyID = true;
            }
        }
        if (worker.getId() != null && worker.getId()>0 && !isEquallyID) {
            id = true;
        }
        return id;
    }

    /**
     * Проверка поля name для checkWorker
     *
     * @param worker проверяемый объект
     * @return true-правильное,false-неправильное
     */
    public boolean checkName(Worker worker) {
        boolean isCorrect = false;
        String name = worker.getName();
        if (name != null) {
            String[] nameArray = name.trim().split(" +");
            if (name.length() != 0 | nameArray[0].length() != 0) {
                isCorrect = true;
            }
        }
        return isCorrect;
    }

    /**
     * Проверка объекта статического класса Coordinates для checkWorker
     *
     * @param worker проверяемый объект
     * @return true-правильный,false-неправильный
     */
    public boolean checkCoordinates(Worker worker) {
        boolean isCorrect = false;
        if (worker.getCoordinates() != null) {
            Integer x = worker.getCoordinates().getX();
            Double y = worker.getCoordinates().getY();
            if (x != null && x > -716) {
                if (y != null && y <= 943) {
                    isCorrect = true;
                }
            }
        }
        return isCorrect;
    }

    /**
     * Проверка поля salary для checkWorker
     *
     * @param worker проверяемый объект
     * @return true-правильное,false-неправильное
     */
    public boolean checkSalary(Worker worker) {
        boolean isCorrect = false;
        if (worker.getSalary() != null && worker.getSalary() > 0) {
            isCorrect = true;
        }
        return isCorrect;
    }

    /**
     * Проверка поля position для checkWorker
     *
     * @param worker проверяемый объект
     * @return true-правильное,false-неправильное
     */
    public boolean checkPosition(Worker worker) {
        boolean isCorrect = false;
        if (worker.getPosition() != null) {
            isCorrect = true;
        }
        return isCorrect;
    }

    /**
     * Проверка поля status для checkWorker
     *
     * @param worker проверяемый объект
     * @return true-правильное,false-неправильное
     */
    public boolean checkStatus(Worker worker) {
        boolean isCorrect = false;
        if (worker.getStatus() != null) {
            isCorrect = true;
        }
        return isCorrect;
    }

    /**
     * Проверка объекта статического класса Person для checkWorker
     *
     * @param worker проверяемый объект
     * @return true-правильный,false-неправильный
     */
    public boolean checkPerson(Worker worker,TreeMap<Integer, Worker> workerList) {
        boolean isCorrect = false;
        if (worker.getPerson() != null) {
            if (worker.getPerson().getBirthday() != null) {
                if (worker.getPerson().getHeight() > 0) {
                    String[] passport = worker.getPerson().getPassportID().trim().split(" +");
                    boolean isEqually = false;
                    for (Map.Entry<Integer, Worker> entry : workerList.entrySet()) {
                        if (entry.getValue().getPerson().getPassportID().equals(worker.getPerson().getPassportID())) {
                            isEqually = true;
                        }
                    }
                    if (worker.getPerson().getPassportID().length() <= 44 && !isEqually && (passport.length == 0 || passport[0].length() != 0)) {
                        isCorrect = true;
                    }
                }
            }
        }
        return isCorrect;
    }
}
