package commands;

import collections.Organization;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;
import managers.OrganizationAsker;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class AddIfMaxCommand extends AbstractCommand {

    private final CollectionManager collectionManager;
    private final OrganizationAsker organizationAsker;

    public AddIfMaxCommand(CollectionManager collectionManager, OrganizationAsker organizationAsker) {
        super("add_if_max {element}", "add a new element to the collection if its value is greater than the value of the largest element in this collection");
        this.collectionManager = collectionManager;
        this.organizationAsker = organizationAsker;
    }

    /**
     * If the collection is empty, the organization is added to the collection.
     * Otherwise, the organization is added to the collection only if its annual turnover is higher
     * than the annual turnover of the
     * organization with the highest annual turnover in the collection
     * 
     * @param argument The argument that the user entered in the command line.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try{
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            Organization askerOrganization = new Organization(
                    organizationAsker.setId(),
                    organizationAsker.askName(),
                    organizationAsker.askCoordinates(),
                    organizationAsker.askCreationDate(),
                    organizationAsker.askAnnualTurnover(),
                    organizationAsker.askEmployeesCount(),
                    organizationAsker.askOrganizationType(),
                    organizationAsker.askAddress()
            );

            if(collectionManager.getCollection().size() == 0){
                collectionManager.addToCollection(askerOrganization);
                Console.printLn("Organization was added successfully");
                return true;
            }

            Organization maxOrganization = collectionManager.getCollection()
                    .stream()
                    .max(Comparator.comparing(Organization::getAnnualTurnover))
                    .orElseThrow(NoSuchElementException::new);

            if(askerOrganization.getAnnualTurnover() > maxOrganization.getAnnualTurnover()){
                collectionManager.addToCollection(askerOrganization);
                Console.printLn("Organization was added successfully");
            } else {
                Console.printLn("Organization annual turnover is not enough to add");
            }
            return true;
        } catch (WrongAmountOfElementsException e){
            Console.printError("Usage of (" + argument + ") in " + getName());
        } catch (IncorrectInputInScriptException ignored) {
        }
        return false;
    }
}
