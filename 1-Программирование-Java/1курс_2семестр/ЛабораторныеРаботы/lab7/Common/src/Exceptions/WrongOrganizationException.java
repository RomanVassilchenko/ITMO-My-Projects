package Exceptions;

import java.io.IOException;

public class WrongOrganizationException extends IOException {
    public WrongOrganizationException(String line) {
        System.err.println("Данные внутри " + line + " в SpaceMarine не соответствуют требованиям! Исправьте, пожалуйста, данное поле");
    }
}
