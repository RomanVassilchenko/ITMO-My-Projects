package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

public class ExitCommand extends AbstractCommand {
    private boolean isComplete;
    public ExitCommand() {
        super("exit", "terminate program (without saving to file)");
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void execute(String argument) {
        isComplete = false;
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
        } catch (WrongAmountOfElementsException exception) {
            Console.printLn("Использование: '" + getName() + "'");
        }
        isComplete = true;
        Console.printLn("Exit");
        System.exit(0);
    }
}
