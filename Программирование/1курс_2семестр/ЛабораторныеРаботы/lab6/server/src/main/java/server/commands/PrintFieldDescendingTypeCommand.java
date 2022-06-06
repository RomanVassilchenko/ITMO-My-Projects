package server.commands;

import common.data.OrganizationType;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Prints all the organization types in the system in descending order
 */
public class PrintFieldDescendingTypeCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingTypeCommand(CollectionManager collectionManager) {
        super("print_field_descending_type","", "display the values of the type field of all elements in descending order");
        this.collectionManager = collectionManager;
    }

    /**
     * Prints all the organization types in the system
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ArrayList<OrganizationType> organizationTypes = new ArrayList<>(collectionManager.getAllOrganizationTypes());
            organizationTypes.sort(Collections.reverseOrder());
            StringBuilder output = new StringBuilder();
            for (OrganizationType organizationType : organizationTypes) {
                if (organizationType == null) output.append("null" + '\n');
                else output.append(organizationType);
            }

            ResponseOutputer.appendLn(output);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendLn("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}
