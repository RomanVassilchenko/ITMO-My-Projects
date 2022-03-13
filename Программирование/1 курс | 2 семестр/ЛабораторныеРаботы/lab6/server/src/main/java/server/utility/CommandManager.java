package server.utility;


import common.exceptions.HistoryIsEmptyException;
import server.commands.ICommand;

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
    private final ICommand serverExitCommand;

    public CommandManager(ICommand addCommand, ICommand addIfMaxCommand, ICommand clearCommand, ICommand executeScriptCommand, ICommand exitCommand, ICommand historyCommand, ICommand infoCommand, ICommand printAscendingCommand, ICommand printDescendingCommand, ICommand printFieldDescendingTypeCommand, ICommand removeAtCommand, ICommand removeByIdCommand, ICommand saveCommand, ICommand showCommand, ICommand shuffleCommand, ICommand updateCommand, ICommand helpCommand, ICommand serverExitCommand) {
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
        this.serverExitCommand = serverExitCommand;

        commands = new ArrayList<>(Arrays.asList(addCommand, addIfMaxCommand, clearCommand, executeScriptCommand,
                exitCommand, historyCommand, infoCommand, printAscendingCommand, printDescendingCommand,
                printFieldDescendingTypeCommand, removeAtCommand, removeByIdCommand, saveCommand, showCommand,
                shuffleCommand, updateCommand, helpCommand, serverExitCommand));
    }

    /**
     * @return List of manager's commands.
     */
    public List<ICommand> getCommands() {
        return commands;
    }

    /**
     * Adds command to command history.
     *
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore) {

        for (ICommand command : commands) {
            if (command.getName().equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE - 1; i > 0; i--) {
                    commandHistory[i] = commandHistory[i - 1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * Prints info about the all commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean help(String stringArgument, Object objectArgument) {
        if (helpCommand.execute(stringArgument, objectArgument)) {
            for (ICommand command : commands) {
                ResponseOutputer.appendTable(command.getName() + " " + command.getUsage(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean info(String stringArgument, Object objectArgument) {
        return infoCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean show(String stringArgument, Object objectArgument) {
        return showCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean add(String stringArgument, Object objectArgument) {
        return addCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean update(String stringArgument, Object objectArgument) {
        return updateCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeById(String stringArgument, Object objectArgument) {
        return removeByIdCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean clear(String stringArgument, Object objectArgument) {
        return clearCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean save(String stringArgument, Object objectArgument) {
        return saveCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean exit(String stringArgument, Object objectArgument) {
        save(stringArgument, objectArgument);
        return exitCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean executeScript(String stringArgument, Object objectArgument) {
        return executeScriptCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean addIfMax(String stringArgument, Object objectArgument) {
        return addIfMaxCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean printAscending(String stringArgument, Object objectArgument) {
        return printAscendingCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Prints the history of used commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean history(String stringArgument, Object objectArgument) {
        if (historyCommand.execute(stringArgument, objectArgument)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                ResponseOutputer.appendLn("Last used commands:");
                for (String command : commandHistory) {
                    if (command != null) ResponseOutputer.appendLn(" " + command);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ResponseOutputer.appendLn("No commands have been used yet!");
            }
        }
        return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean printDescending(String stringArgument, Object objectArgument) {
        return printDescendingCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean printFieldDescendingType(String stringArgument, Object objectArgument) {
        return printFieldDescendingTypeCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeAt(String stringArgument, Object objectArgument) {
        return removeAtCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean shuffle(String stringArgument, Object objectArgument) {
        return shuffleCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean serverExit(String stringArgument, Object objectArgument) {
        save(stringArgument, objectArgument);
        return serverExitCommand.execute(stringArgument, objectArgument);
    }
}
