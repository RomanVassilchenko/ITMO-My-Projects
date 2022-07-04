package commands;

import collections.OrganizationType;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Prints all the organization types in the system in descending order
 */
public class PrintFieldDescendingTypeCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingTypeCommand(CollectionManager collectionManager) {
        super("print_field_descending_type", "display the values of the type field of all elements in descending order");
        this.collectionManager = collectionManager;
    }

    /**
     * Prints all the organization types in the system
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            ArrayList<OrganizationType> organizationTypes = new ArrayList<>(collectionManager.getAllOrganizationTypes());
            organizationTypes.sort(Collections.reverseOrder());
            for (OrganizationType organizationType : organizationTypes) {
                if (organizationType == null) Console.printLn("null");
                else Console.printLn(organizationType.toString());
            }
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        }
        return false;
    }
}
