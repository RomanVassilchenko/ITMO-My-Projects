package сommands;
import models.ICommand;
import modules.MenuModule;
import java.util.ArrayList;
import lab1.commands.SystemsOfLinearAlgebraicEquations;

public class Main implements ICommand {
    @Override
    public String getMessage() {
        return "Назад <--";
    }

    @Override
    public void execute() {
        ArrayList<ICommand> commands = new ArrayList<>();
        //Lab1
        commands.add(new SystemsOfLinearAlgebraicEquations());
        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }
}
