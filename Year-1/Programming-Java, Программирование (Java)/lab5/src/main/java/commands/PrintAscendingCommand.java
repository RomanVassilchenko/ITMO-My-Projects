package commands;

import collections.Organization;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Prints the list of organizations in the collection in alphabetical order
 */
public class PrintAscendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;


    public PrintAscendingCommand(CollectionManager collectionManager) {
        super("print_ascending", "display the elements of the collection in ascending order");
        this.collectionManager = collectionManager;
    }

    
    /**
     * Prints the list of organizations in the collection in alphabetical order
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
        ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
        Collections.sort(copyOfCollection);
        for(Organization organization : copyOfCollection){
            Console.printLn(organization.toString());
        }
        return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        }
        return false;
    }
}


