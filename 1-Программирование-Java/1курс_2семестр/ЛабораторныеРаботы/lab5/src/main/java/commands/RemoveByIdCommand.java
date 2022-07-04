package commands;

import exceptions.MustBeNotEmptyException;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Remove an organization from the collection
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id id", "remove element from collection by its id");
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
            if(collectionManager.getById(id) == null) throw new MustBeNotEmptyException();
            collectionManager.removeByIdFromCollection(id);
            Console.printLn("Organization was removed successfully");
            return true;

        } catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        } catch (NumberFormatException e) {
            Console.printError("The id have to be an Integer value");
        } catch (MustBeNotEmptyException e) {
            Console.printError("There is no organization with this id");
        }
        return false;
    }
}
