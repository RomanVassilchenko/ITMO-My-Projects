package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Command 'clear'. Clears the collection.
 */
public class ClearCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private boolean isComplete;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "clear the collection");
        this.collectionManager = collectionManager;
    }
    /**
     * Returns true if the current state of the command is complete
     * 
     * @return The method is returning a boolean value.
     */
    @Override
    public boolean isComplete() {
        return isComplete;
    }


    /**
     * Clear the collection
     */
    @Override
    public void execute(String argument) {
        isComplete = false;
        try {
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.clearCollection();
            Console.printLn("Collection was cleared");
            isComplete = true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Usage of (" + argument + ") in " + getName());
        }
    }
}
