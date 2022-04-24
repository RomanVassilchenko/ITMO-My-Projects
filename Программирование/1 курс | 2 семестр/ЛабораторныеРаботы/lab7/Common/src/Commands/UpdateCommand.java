package Commands;

import Data.Organization;
import Data.Organizations;
import Data.User;
import Exceptions.NotDatabaseUpdateException;
import Exceptions.OrganizationNotFound;

import java.sql.SQLException;
import java.util.Scanner;

public class UpdateCommand extends Command implements Fillable {

    public UpdateCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);
            if (user != null) {
                if (organizations.isEmpty()) {
                    return "The command cannot be executed because the collection is empty. " +
                            "Add items to the collection using the add command";
                } else {
                    int id = Integer.parseInt((String) args[0]);
                    Organization organization = (Organization) args[1];
                    try {
                        Organization dbOrganization = db.selectOrganization(id);
                        if (user.getLogin().equals(dbOrganization.getOwner().getLogin())) {
                            db.update(id, organization);
                            CommandManager.updateCollection();
                            return "The object was successfully updated";
                        }
                        return "You do not have the rights to change this object";
                    } catch (OrganizationNotFound | SQLException | NotDatabaseUpdateException organizationNotFound) {
                        organizationNotFound.printStackTrace();
                    }
                }
            }
            return "You are not logged in";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public Object[] fill(Scanner scanner) {
        Object[] args = new Object[1];
        args[0] = Organization.fillOrganization(scanner);
        return args;
    }
}