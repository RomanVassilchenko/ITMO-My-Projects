package commands;

import collections.Organization;
import managers.CollectionManager;
import managers.Console;
import managers.OrganizationAsker;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Command 'print_ascending'. Print elements of collection in ascending order.
 */
public class PrintAscendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private boolean isComplete;


    public PrintAscendingCommand(CollectionManager collectionManager) {
        super("PrintAscending", "Print elements of collection in ascending order");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Executes the command.
     */
    @Override
    public void execute(String... arguments) {
        ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
        Collections.sort(copyOfCollection);
        for(Organization organization : copyOfCollection){
            Console.printLn(organization.toString());
        }
    }
}


