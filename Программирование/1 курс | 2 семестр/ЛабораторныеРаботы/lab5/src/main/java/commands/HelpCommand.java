package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

public class HelpCommand extends AbstractCommand {
    private boolean isComplete;

    public HelpCommand() {
        super("help", "display help on available commands");
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
            Console.printLn("Usage: '" + getName() + "'");
        }
    }
}
