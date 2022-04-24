package Commands;

import Data.Organizations;
import Data.User;

public class ExitCommand extends Command {
    public ExitCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);
            if (user != null) {
                System.exit(0);
                return "The program has been successfully completed!";
            }
            return "You are not logged in";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}
