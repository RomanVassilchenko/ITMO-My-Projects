package commands;

import collections.Organization;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
import managers.OrganizationAsker;


/**
 * The class is responsible for adding an organization to the collection
 */
public class AddCommand extends AbstractCommand{

    private final CollectionManager collectionManager;
    private final OrganizationAsker organizationAsker;

    public AddCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker) {
        super("add {element}", "add a new element to the collection");
        this.collectionManager = collectionManager;
        this.organizationAsker = organizationAsker;
    }


    
    /**
     * The function adds an organization to the collection
     * 
     * @param argument The argument that the user entered.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
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
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Usage of (" + argument + ") in " + getName());
        } catch (IncorrectInputInScriptException ignored) {
        }
        return false;
    }
}
