package commands;

import collections.Organization;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
import managers.OrganizationAsker;

/**
 * Command 'add'. Add an element to the collection.
 */
public class AddCommand extends AbstractCommand{

    private final CollectionManager collectionManager;
    private boolean isComplete;
    private final OrganizationAsker organizationAsker;

    public AddCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker) {
        super("add {element}", "add a new element to the collection");
        this.collectionManager = collectionManager;
        this.organizationAsker = organizationAsker;
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
     * Add an organization to the collection
     */
    @Override
    public void execute(String argument) {
        isComplete = false;
        try{
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
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
        } catch (WrongAmountOfElementsException e){
            Console.printError("Usage of (" + argument + ") in " + getName());
        }
    }
}
