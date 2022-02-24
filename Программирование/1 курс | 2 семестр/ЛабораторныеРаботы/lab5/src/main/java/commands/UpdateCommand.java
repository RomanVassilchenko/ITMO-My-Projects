package commands;

import collections.Organization;
import exceptions.MustBeNotEmptyException;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
import managers.OrganizationAsker;

public class UpdateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private boolean isComplete;
    private final OrganizationAsker organizationAsker;

    public UpdateCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker) {
        super("update id {element}", "update the value of the collection element whose id is equal to the given one");
        this.collectionManager = collectionManager;
        this.organizationAsker = organizationAsker;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void execute(String argument) {
        isComplete = false;
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
            isComplete = true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        } catch (NumberFormatException e) {
            Console.printError("The id have to be an Integer value");
        } catch (MustBeNotEmptyException e) {
            Console.printError("There is no organization with this id");
        }
    }
}
