package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * The ShuffleCommand class is a command that shuffles the elements of the collection randomly
 */

public class ShuffleCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public ShuffleCommand(CollectionManager collectionManager) {
        super("shuffle","", "shuffle the elements of the collection randomly");
        this.collectionManager = collectionManager;
    }

    /**
     * Shuffle the collection
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            collectionManager.shuffleCollection();
            ResponseOutputer.appendLn("Collection was shuffled");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
