package Reader;

import Commands.*;
import Data.Organization;
import Data.User;
import Exceptions.CommandAlreadyExistsException;

import java.lang.reflect.InvocationTargetException;

public class CommandInitializer {
    public static CommandManager initCommands(CommandManager manager) throws CommandAlreadyExistsException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        manager.initCommand(ExitCommand.class, "exit", "Exit, without saving");
        manager.initCommand(InfoCommand.class, "info", "Display information about the collection");
        manager.initCommand(HelpCommand.class , "help", "Displays help for commands");
        manager.initCommand(ShowCommand.class , "show", "Outputs all elements of the collection");
        manager.initCommand(AddCommand.class , "add", "Adds an item to the collection", Organization.class );
        manager.initCommand(ClearCommand.class, "clear", "Clears the collection");
        manager.initCommand(UpdateCommand.class, "update", "Updates the value of the element by id", String.class, Organization.class);
        manager.initCommand(InsertAtIndexCommand.class, "insert_at_index", "Updates the value of an element by index", String.class, Organization.class);
        manager.initCommand(RemoveByIdCommand.class, "remove_by_id", "Deletes an element with the specified id", String.class);
        manager.initCommand(RemoveAtIndexCommand.class, "remove_at_index", "Deletes an element with the specified id", String.class);
        manager.initCommand(AddIfMaxCommand.class, "add_if_max", "Adds an item to the collection if it exceeds the others", Organization.class);
        manager.initCommand(ExecuteScriptCommand.class, "execute_script", "Reads and executes a script from a file", String.class);
        manager.initCommand(PrintAscendingCommand.class, "print_ascending", "Outputs all elements in ascending order");
        manager.initCommand(PrintDescendingCommand.class, "print_descending", "Outputs all elements in descending order");
        manager.initCommand(PrintFieldDescendingTypeCommand.class, "print_field_descending_type", "Outputs all element's type in descending order");
        manager.initCommand(ShuffleCommand.class, "shuffle", "Shuffle all elements in collection");
        manager.initCommand(AuthCommand.class, "auth", "Authorizes the user", User.class);
        manager.initCommand(RegisterCommand.class, "register", "Registers a user", User.class);
        return manager;
    }
}
