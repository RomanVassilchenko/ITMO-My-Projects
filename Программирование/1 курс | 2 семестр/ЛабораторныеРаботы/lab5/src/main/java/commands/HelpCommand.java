package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * The `HelpCommand` class is a command that displays the help of the available commands
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "display help on available commands");
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
