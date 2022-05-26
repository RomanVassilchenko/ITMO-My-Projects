package Commands;

import Data.Organization;
import Data.Organizations;
import Data.User;

public class RemoveAtIndexCommand extends Command {
    public RemoveAtIndexCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);
            if (user != null) {
                try {
                    int i = 0;
                    int index = Integer.parseInt((String) args[0]);
                    int id = 0;
                    for (Organization p : organizations) {
                        i++;
                        if (index == i) {
                            id = p.getId();
                        }
                    }
                    Organization organization=db.selectOrganization(id);
                    if (user.getLogin().equals(organization.getOwner().getLogin())) {
                        db.deleteNote(id);
                        CommandManager.updateCollection();
                        return "The object was successfully deleted";
                    }
                    return "The object was successfully deleted";
                } catch (Exception e) {
                    return "You entered the data incorrectly.";
                }
            }
            return "You are not logged in";
        } catch (IllegalArgumentException e) {
            return "Error in arguments!";
        }

    }
}
