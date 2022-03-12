package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Remove an organization from the collection
 */
public class RemoveAtCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveAtCommand(CollectionManager collectionManager) {
        super("remove_at" ,"<Position>", "remove the element at the given position in the collection (index)");
        this.collectionManager = collectionManager;
    }

    /**
     * Remove an organization from the collection
     * @return the response of right execution
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int id = Integer.parseInt(stringArgument);
            collectionManager.removeAtInCollection(id);
            ResponseOutputer.appendLn("Organization was removed successfully");
            return true;
        }catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendLn("Usage: '" + getName() + " " + getUsage() + "'");
        } catch (NumberFormatException e) {
            ResponseOutputer.appendError("The id have to be an Integer value");
        } catch (ArrayIndexOutOfBoundsException exception){
            ResponseOutputer.appendError("Id must be in collection bound");
        } catch (CollectionIsEmptyException e) {
            ResponseOutputer.appendError("Collection is empty!");
        }
        return false;
    }
}
