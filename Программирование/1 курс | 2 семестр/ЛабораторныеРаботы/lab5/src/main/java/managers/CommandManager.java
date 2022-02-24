package managers;

import commands.ICommand;
import exceptions.HistoryIsEmptyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Operates the commands.
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
     * @return The command history.
     */
    public String[] getCommandHistory() {
        return commandHistory;
    }

    /**
     * @return List of manager's commands.
     */
    public List<ICommand> getCommands() {
        return commands;
    }

    /**
     * Adds command to command history.
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore) {

        for (ICommand command : commands) {
            if (command.getName().split(" ")[0].equals(commandToStore)) {
                System.arraycopy(commandHistory, 0, commandHistory, 1, COMMAND_HISTORY_SIZE - 1);
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * Prints that command is not found.
     * @param command Comand, which is not found.
     * @return Command exit status.
     */
    public boolean noSuchCommand(String command) {
        Console.printLn("Command '" + command + "' was not found. Try to write 'help' for more info.");
        return false;
    }

    /**
     * Prints info about the all commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean help(String argument) {
        helpCommand.execute(argument);
        if (!helpCommand.isComplete()) {
            for (ICommand command : commands) {
                Console.printLn(command.getName() + " - " + command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean info(String argument) {
        infoCommand.execute(argument);
        return infoCommand.isComplete();
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean show(String argument) {
        showCommand.execute(argument);
        return showCommand.isComplete();
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean add(String argument) {
        addCommand.execute(argument);
        return addCommand.isComplete();
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean update(String argument) {
        updateCommand.execute(argument);
        return updateCommand.isComplete();
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeById(String argument) {
        removeByIdCommand.execute(argument);
        return removeAtCommand.isComplete();
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean clear(String argument) {
        clearCommand.execute(argument);
        return clearCommand.isComplete();
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean save(String argument) {
        saveCommand.execute(argument);
        return saveCommand.isComplete();
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean exit(String argument) {
        exitCommand.execute(argument);
        return exitCommand.isComplete();
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean executeScript(String argument) {
        executeScriptCommand.execute(argument);
        return executeScriptCommand.isComplete();
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean addIfMax(String argument) {
        addIfMaxCommand.execute(argument);
        return addIfMaxCommand.isComplete();
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean shuffle(String argument) {
        shuffleCommand.execute(argument);
        return shuffleCommand.isComplete();
    }
    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean printAscending(String argument) {
        printAscendingCommand.execute(argument);
        return printAscendingCommand.isComplete();
    }
    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean printDescending(String argument) {
        printDescendingCommand.execute(argument);
        return printDescendingCommand.isComplete();
    }
    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean printFieldDescendingType(String argument) {
        printFieldDescendingTypeCommand.execute(argument);
        return printFieldDescendingTypeCommand.isComplete();
    }



    /**
     * Prints the history of used commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean history(String argument) {
        historyCommand.execute(argument);
        if (historyCommand.isComplete()) {
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


    @Override
    public String toString() {
        return "CommandManager (helper class for working with commands)";
    }
}
