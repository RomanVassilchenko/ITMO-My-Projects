package managers;

import exceptions.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * The Console class is a class that allows you to print to the console.
 */
public class Console {

    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";

    private final CommandManager commandManager;
    private final Scanner userScanner;
    private final OrganizationAsker organizationAsker;
    private final List<String> scriptStack = new ArrayList<>();

    public Console(CommandManager commandManager, Scanner userScanner, OrganizationAsker organizationAsker) {
        this.commandManager = commandManager;
        this.userScanner = userScanner;
        this.organizationAsker = organizationAsker;

    }

    /**
     * The function is used to execute a script
     * 
     * @param argument The name of the script file.
     * @return The return value is an integer that represents the status of the command.
     */
    public int scriptMode(String argument) {
        String[] userCommand;
        int commandStatus;
        scriptStack.add(argument);
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = organizationAsker.getUserScanner();
            organizationAsker.setUserScanner(scriptScanner);
            organizationAsker.setScriptMode();
            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                Console.printLn(Console.PS1 + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script)) throw new ScriptRecursionException();
                    }
                }
                commandStatus = launchCommand(userCommand);
            } while (commandStatus == 0 && scriptScanner.hasNextLine());
            organizationAsker.setUserScanner(tmpScanner);
            organizationAsker.setUserMode();
            if (commandStatus == 1 && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty()))
                Console.printLn("Check script for correct input data!");
            return commandStatus;
        } catch (FileNotFoundException exception) {
            Console.printError("File was not found!");
        } catch (NoSuchElementException exception) {
            Console.printError("Script file is empty!");
        } catch (ScriptRecursionException exception) {
            Console.printError("Scripts can't be recursive!");
        } catch (IllegalStateException exception) {
            Console.printError("Unexpected error!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size()-1);
        }
        return 1;
    }

    /**
     * This function is the interactive mode of the program. It will keep asking the user for input
     * until the user enters "exit"
     */
    public void interactiveMode() {
        String[] userCommand;
        int commandStatus;
        try {
            do {
                Console.print(PS1);
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
     * Prints the given object to the console
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
     * The function takes a String array as an argument, and checks the first element of the array. If
     * the first element is empty, the function does nothing. If the first element is "help", the
     * function checks if the second element is empty. If it is, the function prints the help message
     * for the command "help". If it is not empty, the function checks if the command exists. If it
     * does, the function prints the help message for the command. If it does not, the function prints
     * the message "No such command". If the first element is "info", the function checks if the second
     * element is empty. If it is, the function prints the info message for the command "info". If it
     * is not empty, the function checks if the command exists. If it does, the function prints the
     * info message for the command. If it does not, the function prints the message "No such command".
     * If the first element is "show", the function checks if the second element
     * 
     * @param userCommand the command entered by the user
     * @return The return value is an integer that is either 0, 1, or 2.
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
            case "remove_at":
                if (!commandManager.removeAt(userCommand[1])) return 1;
                break;
            case "clear":
                if (!commandManager.clear(userCommand[1])) return 1;
                break;
            case "save":
                if (!commandManager.save(userCommand[1])) return 1;
                break;
            case "execute_script":
                if (!commandManager.executeScript(userCommand[1])) return 1;
                else return scriptMode(userCommand[1]);
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
