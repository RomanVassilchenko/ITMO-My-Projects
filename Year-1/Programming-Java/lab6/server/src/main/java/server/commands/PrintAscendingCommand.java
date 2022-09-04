package server.commands;

import common.data.Organization;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Prints the list of organizations in the collection in alphabetical order
 */
public class PrintAscendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;


    public PrintAscendingCommand(CollectionManager collectionManager) {
        super("print_ascending","", "display the elements of the collection in ascending order");
        this.collectionManager = collectionManager;
    }

    
    /**
     * Prints the list of organizations in the collection in alphabetical order
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();

        ArrayList<Organization> copyOfCollection = new ArrayList<>(collectionManager.getCollection());
        Collections.sort(copyOfCollection);
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


