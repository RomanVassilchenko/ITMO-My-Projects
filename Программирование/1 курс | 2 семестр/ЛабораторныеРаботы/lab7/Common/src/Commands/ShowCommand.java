package Commands;

import Data.Organizations;
import Data.User;


public class ShowCommand extends Command {
    public ShowCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);
            if (user != null) {
                if (organizations.isEmpty()) {
                    return "The collection is empty. There is no data inside the file!";
                } else {
                    Object[] out = organizations.toArray();
                    StringBuilder builder = new StringBuilder();
                    for (Object o : out) {
                        builder.append(o).append("\n");
                    }
                    return builder.toString();

                }
            }
            return "You are not logged in";
        } catch (IllegalArgumentException e) {
            return "Error in arguments!";
        }

    }
}