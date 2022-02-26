package managers;

import commands.ICommand;
import exceptions.HistoryIsEmptyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The CommandManager class is a singleton class that manages all the commands in the CLI
 */
public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 8;

    private final String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    private final List<ICommand> commands;
    private final ICommand addCommand;
    private final ICommand addIfMaxCommand;
    private final ICommand clearCommand;
    private final ICommand executeScriptCommand;
    private final ICommand exitCommand;
    private final ICommand historyCommand;
    private final ICommand infoCommand;
    private final ICommand printAscendingCommand;
    private final ICommand printDescendingCommand;
    private final ICommand printFieldDescendingTypeCommand;
    private final ICommand removeAtCommand;
    private final ICommand removeByIdCommand;
    private final ICommand saveCommand;
    private final ICommand showCommand;
    private final ICommand shuffleCommand;
    private final ICommand updateCommand;
    private final ICommand helpCommand;

    public CommandManager(ICommand addCommand, ICommand addIfMaxCommand, ICommand clearCommand, ICommand executeScriptCommand, ICommand exitCommand, ICommand historyCommand, ICommand infoCommand, ICommand printAscendingCommand, ICommand printDescendingCommand, ICommand printFieldDescendingTypeCommand, ICommand removeAtCommand, ICommand removeByIdCommand, ICommand saveCommand, ICommand showCommand, ICommand shuffleCommand, ICommand updateCommand, ICommand helpCommand) {
        this.addCommand = addCommand;
        this.addIfMaxCommand = addIfMaxCommand;
        this.clearCommand = clearCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.historyCommand = historyCommand;
        this.infoCommand = infoCommand;
        this.printAscendingCommand = printAscendingCommand;
        this.printDescendingCommand = printDescendingCommand;
        this.printFieldDescendingTypeCommand = printFieldDescendingTypeCommand;
        this.removeAtCommand = removeAtCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.saveCommand = saveCommand;
        this.showCommand = showCommand;
        this.shuffleCommand = shuffleCommand;
        this.updateCommand = updateCommand;
        this.helpCommand = helpCommand;

        commands = new ArrayList<>(Arrays.asList(addCommand, addIfMaxCommand, clearCommand, executeScriptCommand,
                exitCommand, historyCommand, infoCommand, printAscendingCommand, printDescendingCommand,
                printFieldDescendingTypeCommand, removeAtCommand, removeByIdCommand, saveCommand, showCommand,
                shuffleCommand, updateCommand, helpCommand));
    }

    
    /**
     * Add the command to the command history
     * 
     * @param commandToStore The command that the user typed.
     */
    public void addToHistory(String commandToStore) {

        for (ICommand command : commands) {
            if (command.getName().split(" ")[0].equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE-1; i>0; i--) {
                    commandHistory[i] = commandHistory[i-1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }

    
    /**
     * If the command is not found, print a message to the user
     * 
     * @param command The command that was not found.
     * @return Nothing.
     */
    public boolean noSuchCommand(String command) {
        Console.printLn("Command '" + command + "' was not found. Try to write 'help' for more info.");
        return false;
    }

    
    /**
     * Prints a list of all commands and their descriptions
     * 
     * @param argument The argument passed to the help command.
     * @return The boolean value of the help command.
     */
    public boolean help(String argument) {
        if (!helpCommand.execute(argument)) {
            for (ICommand command : commands) {
                Console.printLn(command.getName() + " - " + command.getDescription());
            }
            return true;
        } else return false;
    }

    
    /**
     * This function is called when the user types "info" in the command line
     * 
     * @param argument The argument to the command.
     * @return the response of right execution.
     */
    public boolean info(String argument) {
        return infoCommand.execute(argument);
    }

    
    /**
     * This function is called when the user types "show" in the command line
     * 
     * @param argument The argument to the command.
     * @return the response of right execution.
     */
    public boolean show(String argument) {
        return showCommand.execute(argument);
    }

    
    /**
     * This function is called when the user types "add" in the command line
     * 
     * @param argument The argument to be added to the list.
     * @return the response of right execution.
     */
    public boolean add(String argument) {
        return addCommand.execute(argument);
    }

    
    /**
     * The update method takes a String argument and returns a boolean value
     * 
     * @param argument The argument to the command.
     * @return the response of right execution
     */
    public boolean update(String argument) {
        return updateCommand.execute(argument);
    }

    
    /**
     * Remove an organization from the collection by its id
     * 
     * @param argument The argument to pass to the command.
     * @return the response of right execution.
     */
    public boolean removeById(String argument) {
        return removeByIdCommand.execute(argument);
    }

    
    /**
     * Clear the current collection
     * 
     * @param argument The argument to the command.
     * @return the response of right execution.
     */
    public boolean clear(String argument) {
        return clearCommand.execute(argument);
    }

    
    /**
     * The save function takes a string argument and returns a boolean value
     * 
     * @param argument The argument to the command.
     * @return the response of right execution.
     */
    public boolean save(String argument) {
        return saveCommand.execute(argument);
    }

    
    /**
     * The exit function is a method that takes a String argument. 
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    public boolean exit(String argument) {
        return exitCommand.execute(argument);
    }

    
    /**
     * Execute a script
     * 
     * @param argument The argument to pass to the script.
     * @return the response of right execution.
     */
    public boolean executeScript(String argument) {
        return executeScriptCommand.execute(argument);
    }

    
    /**
     * If the element is greater than the current maximum, then add it to the list
     * 
     * @param argument The argument to be added to the list.
     * @return the response of right execution.
     */
    public boolean addIfMax(String argument) {
        return addIfMaxCommand.execute(argument);
    }

    
    /**
     * This function takes a string argument and returns a boolean value
     * 
     * @param argument The argument to the shuffle command.
     * @return the response of right execution.
     */
    public boolean shuffle(String argument) {
        return shuffleCommand.execute(argument);
    }
    
    /**
     * Prints the numbers in the ascending order
     * 
     * @param argument The argument to the command.
     * @return the response of right execution.
     */
    public boolean printAscending(String argument) {
        return printAscendingCommand.execute(argument);
    }
    
    /**
     * Prints the list of items in descending order
     * 
     * @param argument The argument to the command.
     * @return the response of right execution.
     */
    public boolean printDescending(String argument) {
        return printDescendingCommand.execute(argument);
    }
    
    /**
     * Prints the field type in descending order
     * 
     * @param argument The argument to the command.
     * @return the response of right execution.
     */
    public boolean printFieldDescendingType(String argument) {
        return printFieldDescendingTypeCommand.execute(argument);
    }

    /**
     * Remove the element at the specified index
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    public boolean removeAt(String argument){
        return removeAtCommand.execute(argument);
    }


    
    /**
     * If the history command is executed, it prints the last used commands
     * 
     * @param argument The argument passed to the command.
     * @return the response of right execution.
     */
    public boolean history(String argument) {
        if (!historyCommand.execute(argument)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                Console.printLn("Last used commands:");
                for (String s : commandHistory) {
                    if (s != null) Console.printLn(" " + s);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                Console.printLn("You have not entered any command yet");
            }
        }
        return false;
    }


    /**
     * This function is used to print out the string representation of the command manager
     * 
     * @return The string "CommandManager (helper class for working with commands)"
     */
    @Override
    public String toString() {
        return "CommandManager (helper class for working with commands)";
    }
}
