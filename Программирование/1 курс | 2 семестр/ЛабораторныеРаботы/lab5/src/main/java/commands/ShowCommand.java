package commands;

public class ShowCommand extends AbstractCommand {
    private boolean isComplete;
    public ShowCommand() {
        super("show", "print to standard output all elements of the collection in string representation");
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
