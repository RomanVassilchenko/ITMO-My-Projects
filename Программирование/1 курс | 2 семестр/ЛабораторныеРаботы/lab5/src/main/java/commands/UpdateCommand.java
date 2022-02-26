package commands;

import collections.Organization;
import exceptions.IncorrectInputInScriptException;
import exceptions.MustBeNotEmptyException;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
import managers.OrganizationAsker;

/**
 * The class is used to update the value of the collection element whose id is equal to the given one
 */
public class UpdateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final OrganizationAsker organizationAsker;

    public UpdateCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker) {
        super("update id {element}", "update the value of the collection element whose id is equal to the given one");
        this.collectionManager = collectionManager;
        this.organizationAsker = organizationAsker;
    }

    /**
     * The function takes an argument, which is the id of the organization that will be updated. 
     * If the argument is empty, the function throws an exception. 
     * If the argument is not empty, the function checks if the organization with the given id exists. 
     * If it doesn't exist, the function throws an exception. 
     * If the organization exists, the function asks the user to enter the new values for the
     * organization. 
     * The function then replaces the old organization with the new one. 
     * The function returns true if the organization was updated successfully
     * 
     * @param argument The argument that the user entered.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(argument.isEmpty()) throw new WrongAmountOfElementsException();
            int id = Integer.parseInt(argument);
            if(collectionManager.getById(id) == null) throw new MustBeNotEmptyException();
            Organization newOrganization = new Organization(
                    organizationAsker.setId(),
                    organizationAsker.askName(),
                    organizationAsker.askCoordinates(),
                    organizationAsker.askCreationDate(),
                    organizationAsker.askAnnualTurnover(),
                    organizationAsker.askEmployeesCount(),
                    organizationAsker.askOrganizationType(),
                    organizationAsker.askAddress()
            );
            collectionManager.replaceById(id, newOrganization);
            Console.printLn("Organization was updated successfully");
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        } catch (NumberFormatException e) {
            Console.printError("The id have to be an Integer value");
        } catch (MustBeNotEmptyException e) {
            Console.printError("There is no organization with this id");
        } catch (IncorrectInputInScriptException ignored) {
        }
        return false;
    }
}
