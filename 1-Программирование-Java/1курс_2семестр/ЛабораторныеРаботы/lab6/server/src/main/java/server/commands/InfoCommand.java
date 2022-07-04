package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Prints out the information about the collection
 */
public class InfoCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info","", "print information about the collection to the standard output stream (type, initialization date, number of elements, etc.)");
        this.collectionManager = collectionManager;
    }

    /**
     * Prints out the information about the collection
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "initialization has not yet occurred in this session" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "there has not been a save in this session yet" :
                    lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

            ResponseOutputer.appendLn("Information about the collection:");
            ResponseOutputer.appendLn(" Type: " + collectionManager.collectionType());
            ResponseOutputer.appendLn(" Number of elements: " + collectionManager.collectionSize());
            ResponseOutputer.appendLn(" Last save time: " + lastSaveTimeString);
            ResponseOutputer.appendLn(" Last init time: " + lastInitTimeString);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
