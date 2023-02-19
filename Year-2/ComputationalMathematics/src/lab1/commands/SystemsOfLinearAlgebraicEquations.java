package lab1.commands;
import models.ICommand;
import lab1.models.*;
import modules.MenuModule;
import сommands.Main;

import java.util.ArrayList;

public class SystemsOfLinearAlgebraicEquations implements ICommand {
    @Override
    public String getMessage() {
        return "Решение систем линейных уравнений";
    }

    @Override
    public void execute() {
        ArrayList<ICommand> commands = new ArrayList<>();
        commands.add(new ConsoleCommand());
        commands.add(new FileCommand());
        commands.add(new RandomCommand());
        commands.add(new DiagonalRandomCommand());
        commands.add(new Main());
        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }
}
