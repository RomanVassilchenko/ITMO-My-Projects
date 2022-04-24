package Exceptions;

public class SameIdException extends Exception{
    static {
        System.err.println("The collection items in the file have the same id");
    }
}
