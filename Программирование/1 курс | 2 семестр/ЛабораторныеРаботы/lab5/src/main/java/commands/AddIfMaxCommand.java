package commands;

public class AddIfMaxCommand extends AbstractCommand {
    private boolean isComplete;

    public AddIfMaxCommand() {
        super("add_if_max {element}", "add a new element to the collection if its value is greater than the value of the largest element in this collection");
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
