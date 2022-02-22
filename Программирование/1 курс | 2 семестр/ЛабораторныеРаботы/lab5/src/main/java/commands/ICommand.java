package commands;

/**
 * Interface for all commands.
 */
public interface ICommand {
    String getDescription();
    boolean isComplete();
    String getName();
    void execute(String argument);
}
