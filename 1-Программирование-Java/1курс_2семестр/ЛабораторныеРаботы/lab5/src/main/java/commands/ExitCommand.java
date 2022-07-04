package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * The `ExitCommand` class is a command that terminates the program
 */
public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit", "terminate program (without saving to file)");
    }

    /**
     * Prints a message to the console and terminates the program
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.printLn("Exit");
            System.exit(0);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.printLn("Использование: '" + getName() + "'");
        }
        return false;
    }
}
