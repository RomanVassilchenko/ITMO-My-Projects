package managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Operates command input.
 */
public class Console {

    private CommandManager commandManager;
    private Scanner userScanner;
    private OrganizationAsker organizationAsker;
    private List<String> scriptStack = new ArrayList<>();

    public Console(CommandManager commandManager, Scanner userScanner, OrganizationAsker organizationAsker) {
        this.commandManager = commandManager;
        this.userScanner = userScanner;
        this.organizationAsker = organizationAsker;
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
    private boolean launchCommand(String[] userCommand) {
        switch (userCommand[0]) {
            case "":
                break;
            case "help":
                if (!commandManager.help(userCommand[1])) return false;
                break;
            case "info":
                if (!commandManager.info(userCommand[1])) return false;
                break;
            case "show":
                if (!commandManager.show(userCommand[1])) return false;
                break;
            case "add":
                if (!commandManager.add(userCommand[1])) return false;
                break;
            case "update":
                if (!commandManager.update(userCommand[1])) return false;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(userCommand[1])) return false;
                break;
            case "clear":
                if (!commandManager.clear(userCommand[1])) return false;
                break;
            case "save":
                if (!commandManager.save(userCommand[1])) return false;
                break;
            case "execute_script":
                if (!commandManager.executeScript(userCommand[1])) return false;
//                else return scriptMode(userCommand[1]); //TODO FIX
            case "add_if_max":
                if (!commandManager.addIfMax(userCommand[1])) return false;
                break;
            case "shuffle":
                if (!commandManager.shuffle(userCommand[1])) return false;
                break;
            case "history":
                if (!commandManager.history(userCommand[1])) return false;
                break;
            case "print_ascending":
                if (!commandManager.printAscending(userCommand[1])) return false;
                break;
            case "print_descending":
                if (!commandManager.printDescending(userCommand[1])) return false;
                break;
            case "print_field_descending_type":
                if (!commandManager.printFieldDescendingType(userCommand[1])) return false;
                break;
            case "exit":
                if (!commandManager.exit(userCommand[1])) return false;
            default:
                if (!commandManager.noSuchCommand(userCommand[0])) return false;
        }
        return true;
    }
}
