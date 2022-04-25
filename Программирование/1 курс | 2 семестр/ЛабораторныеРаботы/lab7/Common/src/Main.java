import Commands.*;
import Exceptions.CommandAlreadyExistsException;
import Exceptions.NotFoundCommandException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import static Reader.CommandInitializer.initCommands;

public class Main {

    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";


    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, CommandAlreadyExistsException, ClassNotFoundException {

        CommandManager manager = CommandManager.getInstance(System.getenv("SQL_LOGIN"), System.getenv("SQL_PASSWORD"));
        manager = initCommands(manager);

        CommandManager.updateCollection();
        Scanner scanner = new Scanner(System.in);
        System.out.println("The program has been successfully launched. Enter 'help' to find out the list of commands");


        while (true) {
            try {
                System.out.print(PS1);
                String line = scanner.nextLine().trim();
                String name = CommandManager.parseName(line);
                Object[] arg = CommandManager.parseArgs(line);
                Command command = CommandManager.getCommand(name);
                Object[] fillableArg = CommandManager.getFillableArgs(command, scanner);
                arg = CommandManager.concatArgs(arg, fillableArg);
                try {
                    CommandManager.validate(command, arg);
                    System.out.println(CommandManager.execute(CommandManager.getUser(), command, arg));
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            } catch (NotFoundCommandException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
