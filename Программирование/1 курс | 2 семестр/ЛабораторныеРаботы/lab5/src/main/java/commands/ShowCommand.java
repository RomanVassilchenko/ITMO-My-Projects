package commands;

import collections.Organization;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;
public class ShowCommand extends AbstractCommand {
    private boolean isComplete;
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "print to standard output all elements of the collection in string representation");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void execute(String argument) {
        isComplete = false;
        ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
        for(Organization organization : copyOfCollection){
            Console.printLn(organization.toString());
        }
        isComplete = true;
    }
}
