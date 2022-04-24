package Commands;

import Data.Organizations;
import Data.User;

import java.util.Collections;


public class ShuffleCommand extends Command {
    public ShuffleCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user,DataBase db,Organizations organizations, Object... args) {
        try {
            validate(args);
            if (user != null) {

                Collections.shuffle(organizations);
                return organizations.toString();
            }
            return "You are not logged in";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}