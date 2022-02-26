package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Remove an organization from the collection
 */
public class RemoveAtCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveAtCommand(CollectionManager collectionManager) {
        super("remove_at index", "remove the element at the given position in the collection (index)");
        this.collectionManager = collectionManager;
    }

    /**
     * Remove an organization from the collection
     * 
     * @param argument The argument that the user entered.
     * @return the response of right execution
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(argument.isEmpty()) throw new WrongAmountOfElementsException();
            int id = Integer.parseInt(argument);
            collectionManager.removeAtInCollection(id);
            Console.printLn("Organization was removed successfully");
            return true;
        }catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        } catch (NumberFormatException e) {
            Console.printError("The id have to be an Integer value");
        } catch (ArrayIndexOutOfBoundsException exception){
            Console.printError("Id must be in collection bound");
        }
        return false;
    }
}
