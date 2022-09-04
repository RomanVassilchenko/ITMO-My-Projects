package Commands;

import Data.Organization;
import Data.Organizations;
import Data.User;

public class InsertAtIndexCommand extends Command {
    public InsertAtIndexCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);

            if (organizations.isEmpty()) {
                return "The command cannot be executed because the collection is empty. " +
                        "Add items to the collection using the add command";
            } else {
                try {
                    int index = Integer.parseInt((String) args[0]);
                    Organization organization = (Organization) args[1];
                    int i = 0;
                    int id = 0;
                    for (Organization p : organizations) {
                        i += 1;
                        if (index == i) {
                            id = p.getId();
                        }
                    }
                    if ((index > i) || (index == 0)) return "There is no element with such an index";
                    else {
                        Organization dbOrganization = db.selectOrganization(id);
                        if (user.getLogin().equals(dbOrganization.getOwner().getLogin())) {
                            db.update(id, organization);
                            return "The object was successfully updated";
                        }
                        return "You do not have the rights to change this object";
                    }

                } catch (Exception e) {
                    return "You entered the data incorrectly.";
                }
            }

        } catch (IllegalArgumentException e) {
            return "Error in arguments!";
        }
    }

}