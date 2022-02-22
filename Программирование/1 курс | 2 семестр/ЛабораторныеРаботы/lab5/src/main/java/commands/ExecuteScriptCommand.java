package commands;

public class ExecuteScriptCommand extends AbstractCommand {
    private boolean isComplete;

    public ExecuteScriptCommand() {
        super("execute_script file_name", "read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.");
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
