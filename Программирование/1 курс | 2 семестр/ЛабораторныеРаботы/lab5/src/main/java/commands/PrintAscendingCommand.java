package commands;

import collections.Organization;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Command 'print_ascending'. Print elements of collection in ascending order.
 */
public class PrintAscendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private boolean isComplete;


    public PrintAscendingCommand(CollectionManager collectionManager) {
        super("print_ascending", "display the elements of the collection in ascending order");
        this.collectionManager = collectionManager;
    }

    /**
     * Returns true if the current state of the command is complete, false otherwise
     * 
     * @return The method is returning a boolean value.
     */
    @Override
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Prints out the list of organizations in the collection in alphabetical order
     */
    @Override
    public void execute(String argument) {
        isComplete = false;
        ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
        Collections.sort(copyOfCollection);
        for(Organization organization : copyOfCollection){
            Console.printLn(organization.toString());
        }
    }
}


