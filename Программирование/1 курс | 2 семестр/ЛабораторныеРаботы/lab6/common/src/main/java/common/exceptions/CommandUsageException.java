package common.exceptions;

/**
 * Is throwed when command can't be used.
 */
public class CommandUsageException extends Exception {
    public CommandUsageException() {
        super();
    }

    public CommandUsageException(String message) {
        super(message);
    }
}