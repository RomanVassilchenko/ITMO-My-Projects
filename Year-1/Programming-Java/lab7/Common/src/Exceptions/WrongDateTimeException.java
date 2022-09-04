package Exceptions;

public class WrongDateTimeException extends RuntimeException{
    static {
        System.err.println("Incorrect date/time values");
    }
}