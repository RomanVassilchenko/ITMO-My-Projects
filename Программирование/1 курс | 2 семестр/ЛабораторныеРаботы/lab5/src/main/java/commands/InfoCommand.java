package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

public class InfoCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private boolean isComplete;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "print information about the collection to the standard output stream (type, initialization date, number of elements, etc.)");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void execute(String argument) {
        isComplete = false;
        try{
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.printLn(collectionManager.infoAboutCollection());
            isComplete = true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Usage of (" + argument + ") in " + getName());
        }
    }
}
