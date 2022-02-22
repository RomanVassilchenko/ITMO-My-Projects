package commands;

public class RemoveByIdCommand extends AbstractCommand {
    private boolean isComplete;

    public RemoveByIdCommand() {
        super("remove_by_id id", "remove element from collection by its id");
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
