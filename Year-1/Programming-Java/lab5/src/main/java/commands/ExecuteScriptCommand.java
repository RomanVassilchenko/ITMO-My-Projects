package commands;

import exceptions.WrongAmountOfElementsException;
import managers.Console;

/**
 * Execute a script
 */
public class ExecuteScriptCommand extends AbstractCommand {

    public ExecuteScriptCommand() {
        super("execute_script file_name", "read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.");
    }

    /**
     * Execute a script
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.printLn("Execute script '" + argument + "'...");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.printLn("Usage of: '" + getName() + "'");
        }
        return false;
    }
}
