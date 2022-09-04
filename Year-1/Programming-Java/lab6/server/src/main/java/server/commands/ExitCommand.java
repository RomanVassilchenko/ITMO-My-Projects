package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputer;

/**
 * The `ExitCommand` class is a command that terminates the program
 */
public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit","", "terminate program (without saving to file)");
    }

    /**
     * Prints a message to the console and terminates the program
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
