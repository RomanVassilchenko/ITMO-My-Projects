package server.commands;

/**
 * Interface for all commands
 */
public interface ICommand {
    String getDescription();
    String getName();
    String getUsage();
    boolean execute(String commandStringArgument, Object commandObjectArgument);
}
