package commands;

import collections.Organization;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;
/**
 * The `ShowCommand` class is a command that prints all the elements of the collection in string
 * representation
 */
public class ShowCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "print to standard output all elements of the collection in string representation");
        this.collectionManager = collectionManager;
    }

    /**
     * Prints all the organizations in the collection
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
            for (Organization organization : copyOfCollection) {
                Console.printLn(organization.toString() + "\n============");
            }
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        }
        return false;
    }
}
