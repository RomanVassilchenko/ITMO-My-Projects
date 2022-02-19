package commands;

import collections.Organization;
import managers.CollectionManager;
import managers.Console;
import managers.OrganizationAsker;

import java.util.Scanner;

/**
 * Command 'add'. Add an element to the collection.
 */
public class AddCommand extends AbstractCommand{

    private final CollectionManager collectionManager;
    private boolean isComplete;
    private final OrganizationAsker organizationAsker;

    public AddCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker) {
        super("Add", "Add element to collection");
        this.collectionManager = collectionManager;
        this.organizationAsker = organizationAsker;
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
        try{
            collectionManager.addToCollection(new Organization(
                    organizationAsker.setId(),
                    organizationAsker.askName(),
                    organizationAsker.askCoordinates(),
                    organizationAsker.askCreationDate(),
                    organizationAsker.askAnnualTurnover(),
                    organizationAsker.askEmployeesCount(),
                    organizationAsker.askOrganizationType(),
                    organizationAsker.askAddress()
            ));
            Console.printLn("Organization was created successfully");
            isComplete = true;
        } catch (Exception e){
            Console.printError(e.toString());
        }
    }
}
