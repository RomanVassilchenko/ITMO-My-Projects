package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
/**
 * The ShuffleCommand class is a command that shuffles the elements of the collection randomly
 */

public class ShuffleCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public ShuffleCommand(CollectionManager collectionManager) {
        super("shuffle", "shuffle the elements of the collection randomly");
        this.collectionManager = collectionManager;
    }

    /**
     * Shuffle the collection
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.shuffleCollection();
            Console.printLn("Collection was shuffled");
            return true;

        } catch (WrongAmountOfElementsException e){
            Console.printError("Usage of (" + argument + ") in " + getName());
        }
        return false;
    }
}
