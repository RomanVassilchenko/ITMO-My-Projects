package Exceptions;


public class CommandAlreadyExistsException extends Exception {
    public CommandAlreadyExistsException(String message) {
        super(message);
    }
}
