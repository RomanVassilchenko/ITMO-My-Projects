package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
import managers.FileManager;

/**
 * Write the collection to the file
 */
public class SaveCommand extends AbstractCommand {
    private final FileManager fileManager;
    private final CollectionManager collectionManager;

    public SaveCommand(FileManager fileManager, CollectionManager collectionManager) {
        super("save", "save collection to file");
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    /**
     * Write the collection to the file
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            fileManager.writeCollection(collectionManager.getCollection());
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        }
        return false;
    }
}
