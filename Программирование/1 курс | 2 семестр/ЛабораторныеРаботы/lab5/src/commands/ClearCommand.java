package commands;

import managers.CollectionManager;
import managers.Console;

/**
 * Command 'clear'. Clears the collection.
 */
public class ClearCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private boolean isComplete;
    public ClearCommand(CollectionManager collectionManager) {
        super("Clear", "Clear all elements in collections");
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean isComplete() {
        return isComplete;
    }


    /**
     * Executes the command.
     */
    @Override
    public void execute(String... arguments) {
        collectionManager.clearCollection();
        Console.printLn("Collection was cleared");
        isComplete = true;
    }
}
