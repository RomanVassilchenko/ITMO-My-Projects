package commands;

public class RemoveAtCommand extends AbstractCommand {
    private boolean isComplete;

    public RemoveAtCommand() {
        super("remove_at index", "remove the element at the given position in the collection (index)");
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
