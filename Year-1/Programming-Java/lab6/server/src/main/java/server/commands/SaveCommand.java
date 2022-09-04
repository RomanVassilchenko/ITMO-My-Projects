package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Write the collection to the file
 */
public class SaveCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save","", "save collection to file");
        this.collectionManager = collectionManager;
    }

    /**
     * Write the collection to the file
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            collectionManager.saveCollection();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
