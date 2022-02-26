package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;


/**
 * Clear the collection
 */
public class ClearCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "clear the collection");
        this.collectionManager = collectionManager;
    }

    
    /**
     * Clear the collection
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution..
     */
    @Override
    public boolean execute(String argument) {
        try {
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.clearCollection();
            Console.printLn("Collection was cleared");
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Usage of (" + argument + ") in " + getName());
        }
        return false;
    }
}
