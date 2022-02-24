package commands;

public class HistoryCommand extends AbstractCommand{
    private boolean isComplete;

    public HistoryCommand() {
        super("history", "show the list of the last commands");
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
