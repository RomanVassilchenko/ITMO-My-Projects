package commands;

public class InfoCommand extends AbstractCommand {
    private boolean isComplete;

    public InfoCommand() {
        super("info", "print information about the collection to the standard output stream (type, initialization date, number of elements, etc.)");
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void execute(String argument) {
        isComplete = false;
    }
}
