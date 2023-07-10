package server.commands;

import common.data.Organization;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.util.ArrayList;
import java.util.Collections;


/**
 * The PrintDescendingCommand class is a command that prints the list of organizations in reverse order
 */
public class PrintDescendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;


    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending","", "display the elements of the collection in descending order");
        this.collectionManager = collectionManager;
    }


    
    /**
     * Prints the list of organizations in reverse order
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
            copyOfCollection.sort(Collections.reverseOrder());
            StringBuilder output = new StringBuilder();
            for(Organization organization : copyOfCollection){
                output.append(organization.toString()).append("\n");
            }
            ResponseOutputer.appendLn(output);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}



