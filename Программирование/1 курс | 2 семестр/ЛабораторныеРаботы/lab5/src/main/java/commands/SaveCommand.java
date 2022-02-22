package commands;

public class SaveCommand extends AbstractCommand {
    private boolean isComplete;

    public SaveCommand() {
        super("save", "save collection to file");
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
