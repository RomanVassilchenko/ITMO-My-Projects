package Commands;

import Data.Organizations;
import Data.User;

public class SaveCommand extends Command {
    public SaveCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);
            //organizations.save();
            return "The collection is saved";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
