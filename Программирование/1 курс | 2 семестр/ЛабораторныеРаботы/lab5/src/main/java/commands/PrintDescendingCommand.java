package commands;

import collections.Organization;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Prints the elements of the collection in descending order
 */
public class PrintDescendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private boolean isComplete;


    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", "display the elements of the collection in descending order");
        this.collectionManager = collectionManager;
    }

    /**
     * Returns true if the current state of the command is complete
     * 
     * @return The method is returning a boolean value.
     */
    @Override
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * It sorts the collection in descending order.
     */
    @Override
    public void execute(String argument) {
        isComplete = false;
        ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
        copyOfCollection.sort(Collections.reverseOrder());
        for(Organization organization : copyOfCollection){
            Console.printLn(organization.toString() + "=====");
        }
        isComplete = true;
    }
}



