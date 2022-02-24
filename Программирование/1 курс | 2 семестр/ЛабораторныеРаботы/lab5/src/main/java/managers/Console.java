package managers;

import run.App;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Operates command input.
 */
public class Console {

    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final Scanner userScanner;
    private final OrganizationAsker organizationAsker;
    private final FileManager fileManager;
    private final List<String> scriptStack = new ArrayList<>();

    public Console(CollectionManager collectionManager, CommandManager commandManager, Scanner userScanner, OrganizationAsker organizationAsker, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.userScanner = userScanner;
        this.organizationAsker = organizationAsker;
        this.fileManager = fileManager;

    }

    public void interactiveMode() {
        String[] userCommand;
        int commandStatus;
        try {
            do {
                Console.print(App.PS1);
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                commandManager.addToHistory(userCommand[0]);
                commandStatus = launchCommand(userCommand);
            } while (commandStatus != 2);
        } catch (NoSuchElementException exception) {
            Console.printError("User input not found!");
        } catch (IllegalStateException exception) {
            Console.printError("Unexpected error!");
        }
    }

    /**
     * Prints the given object to the standard output stream
     * 
     * @param toOut The object to print.
     */
    public static void print(Object toOut){
        System.out.print(toOut);
    }
    
    /**
     * Print the given object to the console
     * 
     * @param toOut The object to print to the console.
     */
    public static void printLn(Object toOut) {
        System.out.println(toOut);
    }
    
    /**
     * Prints the error message to the console
     * 
     * @param toOut The object to print out.
     */
    public static void printError(Object toOut) {
        System.out.println("Error: " + toOut);
    }

    /**
     * Launchs the command.
     * @param userCommand Command to launch.
     * @return Exit code.
     */
    private int launchCommand(String[] userCommand) {
        switch (userCommand[0]) {
            case "":
                break;
            case "help":
                if (!commandManager.help(userCommand[1])) return 1;
                break;
            case "info":
                if (!commandManager.info(userCommand[1])) return 1;
                break;
            case "show":
                if (!commandManager.show(userCommand[1])) return 1;
                break;
            case "add":
                if (!commandManager.add(userCommand[1])) return 1;
                break;
            case "update":
                if (!commandManager.update(userCommand[1])) return 1;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(userCommand[1])) return 1;
                break;
            case "clear":
                if (!commandManager.clear(userCommand[1])) return 1;
                break;
            case "save":
                if (!commandManager.save(userCommand[1])) return 1;
                break;
            case "execute_script":
                if (!commandManager.executeScript(userCommand[1])) return 1;
//                else return scriptMode(userCommand[1]); //TODO FIX
            case "add_if_max":
                if (!commandManager.addIfMax(userCommand[1])) return 1;
                break;
            case "shuffle":
                if (!commandManager.shuffle(userCommand[1])) return 1;
                break;
            case "history":
                if (!commandManager.history(userCommand[1])) return 1;
                break;
            case "print_ascending":
                if (!commandManager.printAscending(userCommand[1])) return 1;
                break;
            case "print_descending":
                if (!commandManager.printDescending(userCommand[1])) return 1;
                break;
            case "print_field_descending_type":
                if (!commandManager.printFieldDescendingType(userCommand[1])) return 1;
                break;
            case "exit":
                if (!commandManager.exit(userCommand[1])) return 1;
                else return 2;
            default:
                if (!commandManager.noSuchCommand(userCommand[0])) return 1;
        }
        return 0;
    }
}
