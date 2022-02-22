package commands;

public class ExitCommand extends AbstractCommand {
    private boolean isComplete;
    public ExitCommand() {
        super("exit", "terminate program (without saving to file)");
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
