package commands;

/**
 * Interface for all commands
 */
public interface ICommand {
    String getDescription();
    String getName();
    boolean execute(String argument);
}
