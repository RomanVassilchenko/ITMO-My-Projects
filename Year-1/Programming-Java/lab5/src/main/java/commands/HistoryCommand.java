package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * This class is used to print the list of the last commands
 */
public class HistoryCommand extends AbstractCommand{

    public HistoryCommand() {
        super("history", "show the list of the last commands");
    }

    /**
     * Prints the usage of the command
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
        } catch (WrongAmountOfElementsException exception) {
            Console.printLn("Usage: '" + getName() + "'");
        }
        return false;
    }
}
