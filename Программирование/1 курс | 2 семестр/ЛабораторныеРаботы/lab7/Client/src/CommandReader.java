import Answers.Request;
import Commands.Command;
import Commands.CommandManager;
import Data.User;
import Exceptions.CommandAlreadyExistsException;
import Exceptions.NotFoundCommandException;
import Reader.CommandInitializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;


public class CommandReader {
    private final Sender sender;
    private User user;

    public CommandReader(Sender sender) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, CommandAlreadyExistsException {
        this.sender = sender;
        CommandManager manager = new CommandInitializer().initCommands(CommandManager.getInstance());
    }

    public void read() {
        System.out.println("The program has been successfully launched. Enter 'help' to find out the list of commands");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String line = scanner.nextLine().trim();
                if(line.equals("")) continue;
                readCommand(line, scanner);
                System.out.println();
            } catch (NotFoundCommandException | IllegalArgumentException e) {
                PrintConsole.printerror("There is no such command");
            } catch (ArrayIndexOutOfBoundsException e) {
                PrintConsole.printerror("You didn't specify an argument");
            }
        }
    }

    private void readCommand(String line, Scanner scanner) throws NotFoundCommandException {
        String name = CommandManager.parseName(line);

        switch (name) {
            case "exit":
                System.exit(0);
            case "execute_script": {
                Object[] args = CommandManager.parseArgs(line);
                File file = new File((String) args[0]);
                if (!file.exists())
                    System.err.println("The script does not exist");
                else if (file.exists() && !file.canRead())
                    System.err.println("The script cannot be read, check the file rights (rights to read)");
                else if (file.exists() && !file.canExecute())
                    System.err.println("The script cannot be executed, check the file rights (right to execute)");
                else {
                    try {
                        Scanner fileScanner = new Scanner(file);
                        while (fileScanner.hasNextLine()) {
                            String fileLine = fileScanner.nextLine().trim();

                            if (fileLine.equals(name + ' ' + args[0])) {
                                PrintConsole.printerror("Recursion detected");
                            } else {
                                readCommand(fileLine, fileScanner);
                            }
                        }
                        fileScanner.close();
                    } catch (FileNotFoundException e) {
                        PrintConsole.printerror("The script does not exist");
                    }
                }

                break;
            }
            case "auth": {
                Object[] args = CommandManager.parseArgs(line);
                Command command = CommandManager.getCommand(name);
                Object[] fillableArg = CommandManager.getFillableArgs(command, scanner);
                args = CommandManager.concatArgs(args, fillableArg);
                CommandManager.validate(command, args);
                user = (User) args[0];
                sender.send(new Request(user, command, args));
                break;
            }
            default: {
                Object[] args = CommandManager.parseArgs(line);
                Command command = CommandManager.getCommand(name);
                Object[] fillableArg = CommandManager.getFillableArgs(command, scanner);
                args = CommandManager.concatArgs(args, fillableArg);

                CommandManager.validate(command, args);
                sender.send(new Request(user, command, args));
                break;
            }
        }
    }
}
