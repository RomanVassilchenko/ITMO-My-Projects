package server.commands;

import common.data.Organization;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.OrganizationRaw;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;


/**
 * The class is responsible for adding an organization to the collection
 */
public class AddCommand extends AbstractCommand{

    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add" ,"{element}", "add a new element to the collection");
        this.collectionManager = collectionManager;
    }


    
    /**
     * The function adds an organization to the collection
     *
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try{
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            OrganizationRaw organizationRaw = (OrganizationRaw) objectArgument;
            collectionManager.addToCollection(new Organization(
                    collectionManager.generateNextId(),
                    organizationRaw.getName(),
                    organizationRaw.getCoordinates(),
                    LocalDateTime.now(),
                    organizationRaw.getAnnualTurnover(),
                    organizationRaw.getEmployeesCount(),
                    organizationRaw.getType(),
                    organizationRaw.getPostalAddress()
            ));
            ResponseOutputer.appendLn("Organization was added successfully!");
            return true;
        } catch (WrongAmountOfElementsException e){
            ResponseOutputer.appendLn("Usage: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("The object passed by the client is incorrect!");
        }
        return false;
    }
}
