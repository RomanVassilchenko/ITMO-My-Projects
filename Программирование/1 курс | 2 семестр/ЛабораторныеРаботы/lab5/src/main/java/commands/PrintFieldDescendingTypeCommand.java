package commands;

public class PrintFieldDescendingTypeCommand extends AbstractCommand {
    private boolean isComplete;

    public PrintFieldDescendingTypeCommand() {
        super("print_field_descending_type", "display the values of the type field of all elements in descending order");
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
