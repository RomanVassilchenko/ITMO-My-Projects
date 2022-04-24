package Commands;

import Data.Organizations;
import Data.User;

public class InfoCommand extends Command {
    public InfoCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {

            validate(args);
            if (user != null) {
                return organizations.toString();
            }
            return "You are not logged in";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}
