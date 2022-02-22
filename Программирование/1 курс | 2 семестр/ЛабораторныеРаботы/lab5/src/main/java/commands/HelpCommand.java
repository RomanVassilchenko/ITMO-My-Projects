package commands;

public class HelpCommand extends AbstractCommand {
    private boolean isComplete;

    public HelpCommand() {
        super("help", "display help on available commands");
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
