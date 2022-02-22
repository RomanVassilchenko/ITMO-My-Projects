package commands;

public class UpdateCommand extends AbstractCommand {
    private boolean isComplete;

    public UpdateCommand() {
        super("update id {element}", "update the value of the collection element whose id is equal to the given one");
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
