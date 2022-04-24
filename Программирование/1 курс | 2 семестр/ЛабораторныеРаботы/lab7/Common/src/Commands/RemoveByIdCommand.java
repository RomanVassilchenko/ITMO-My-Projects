package Commands;

import Data.Organization;
import Data.Organizations;
import Data.User;


public class RemoveByIdCommand extends Command {
    public RemoveByIdCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);
            if (user != null) {
                int id = Integer.parseInt((String) args[0]);
                try {
                    Organization organization=db.selectOrganization(id);
                    if (user.getLogin().equals(organization.getOwner().getLogin())) {
                        db.deleteNote(id);
                        CommandManager.updateCollection();
                        return "The object was successfully deleted";
                    }

                    return "You don't have the rights to delete this object";
                } catch (Exception e) {
                    return "You entered the data incorrectly.";
                }
            }
            return "You are not logged in";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}