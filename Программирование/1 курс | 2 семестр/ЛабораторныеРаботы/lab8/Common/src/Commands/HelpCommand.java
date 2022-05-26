package Commands;


import Data.Organizations;
import Data.User;

public class HelpCommand extends Command {
    public HelpCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);
            return CommandManager.getCommandsInfo();
        } catch (IllegalArgumentException e) {
            return "Error in arguments!";
        }
    }
}