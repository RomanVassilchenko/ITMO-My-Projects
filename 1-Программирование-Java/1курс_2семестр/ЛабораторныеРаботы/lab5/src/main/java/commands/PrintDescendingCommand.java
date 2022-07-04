package commands;

import collections.Organization;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;
import java.util.Collections;


/**
 * The PrintDescendingCommand class is a command that prints the list of organizations in reverse order
 */
public class PrintDescendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;


    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", "display the elements of the collection in descending order");
        this.collectionManager = collectionManager;
    }


    
    /**
     * Prints the list of organizations in reverse order
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
        ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
        copyOfCollection.sort(Collections.reverseOrder());
        for(Organization organization : copyOfCollection){
            Console.printLn(organization.toString() + "=====");
        }
        return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        }
        return false;
    }
}



