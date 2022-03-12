package server.commands;

import common.data.Organization;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.OrganizationRaw;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Command 'add_if_max'. Adds a new element to collection if it's more than the maximum one.
 */
public class AddIfMaxCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    public AddIfMaxCommand(CollectionManager collectionManager) {
        super("add_if_max" ,"{element}", "add a new element to the collection if its value is greater than the value of the largest element in this collection");
        this.collectionManager = collectionManager;
    }

    /**
     * If the collection is empty, the organization is added to the collection.
     * Otherwise, the organization is added to the collection only if its annual turnover is higher
     * than the annual turnover of the
     * organization with the highest annual turnover in the collection
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            OrganizationRaw organizationRaw = (OrganizationRaw) objectArgument;
            Organization organizationToAdd = new Organization(
                    collectionManager.generateNextId(),
                    organizationRaw.getName(),
                    organizationRaw.getCoordinates(),
                    LocalDateTime.now(),
                    organizationRaw.getAnnualTurnover(),
                    organizationRaw.getEmployeesCount(),
                    organizationRaw.getType(),
                    organizationRaw.getPostalAddress()
            );

            if(collectionManager.getCollection().size() == 0){
                collectionManager.addToCollection(organizationToAdd);
                ResponseOutputer.appendLn("Organization was added successfully");
                return true;
            }

            Organization maxOrganization = collectionManager.getCollection()
                    .stream()
                    .max(Comparator.comparing(Organization::getAnnualTurnover))
                    .orElseThrow(NoSuchElementException::new);

            if(organizationRaw.getAnnualTurnover() > maxOrganization.getAnnualTurnover()){
                collectionManager.addToCollection(organizationToAdd);
                ResponseOutputer.appendLn("Organization was added successfully");
            } else {
                ResponseOutputer.appendError("Organization annual turnover is not enough to add");
            }
            return true;
        } catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendLn("Usage: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("The object passed by the client is incorrect!");
        }
        return false;
    }
}
