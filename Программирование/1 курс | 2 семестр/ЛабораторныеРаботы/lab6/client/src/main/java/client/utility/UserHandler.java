package client.utility;

import client.App;
import common.data.Address;
import common.data.Coordinates;
import common.data.OrganizationType;
import common.exceptions.CommandUsageException;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.ScriptRecursionException;
import common.interaction.OrganizationRaw;
import common.interaction.Request;
import common.interaction.ResponseResult;
import common.utility.Outputer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class UserHandler {
    private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public UserHandler(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Receives user input.
     *
     * @param serverResponseResult Last server's response code.
     * @return New request to server.
     */
    public Request handle(ResponseResult serverResponseResult) {
        String userInput;
        String[] userCommand;
        ProcessingResult processingResult;
        int rewriteAttempts = 0;
        try{
            do{
                try{
                    if(fileMode() && (serverResponseResult == ResponseResult.ERROR ||
                            serverResponseResult == ResponseResult.SERVER_EXIT))
                        throw new IncorrectInputInScriptException();
                    while(fileMode() && !userScanner.hasNextLine()){
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        Outputer.printLn("Going back to the script '" + scriptStack.pop().getName() + "'...");
                    }
                    if(fileMode()){
                        userInput = userScanner.nextLine();
                        if(!userInput.isEmpty()){
                            Outputer.print(App.PS1);
                            Outputer.printLn(userInput);
                        }
                    } else {
                        Outputer.print(App.PS1);
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    Outputer.println();
                    Outputer.printError("An error occurred while entering the command!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        Outputer.printError("Exceeded the number of input attempts!");
                        System.exit(0);
                    }
                }
                processingResult = processCommand(userCommand[0], userCommand[1]);
            } while (processingResult == ProcessingResult.ERROR && !fileMode() || userCommand[0].isEmpty());
            try{
                if(fileMode() && (serverResponseResult == ResponseResult.ERROR || processingResult == ProcessingResult.ERROR))
                    throw new IncorrectInputInScriptException();
                switch (processingResult){
                    case OBJECT:
                        OrganizationRaw organizationAddRaw = generateOrganizationAdd();
                        return new Request(userCommand[0], userCommand[1], organizationAddRaw);
                    case UPDATE_OBJECT:
                        OrganizationRaw organizationUpdateRaw = generateOrganizationUpdate();
                        return new Request(userCommand[0], userCommand[1], organizationUpdateRaw);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if(!scriptFile.exists()) throw new FileNotFoundException();
                        if(!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        Outputer.printLn("Execute script '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception){
                Outputer.printError("The script file was not found!");
            } catch (ScriptRecursionException exception){
                Outputer.printError("Scripts cannot be called recursively!");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception){
            Outputer.printError("Script execution aborted!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request();
        }
        return new Request(userCommand[0], userCommand[1]);
    }

    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private ProcessingResult processCommand(String command, String commandArgument) {
        try{
            switch (command){
                case "":
                    return ProcessingResult.ERROR;
                case "add":
                case "add_if_max":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingResult.OBJECT;
                case "clear":
                case "exit":
                case "help":
                case "history":
                case "info":
                case "print_ascending":
                case "print_descending":
                case "print_field_descending":
                case "save":
                case "server_exit":
                case "show":
                case "shuffle":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingResult.SCRIPT;
                case "remove_at":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<Position>");
                    break;
                case "remove_by_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    break;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID> {element}");
                    return ProcessingResult.UPDATE_OBJECT;
                default:
                    Outputer.printLn("Command '" + command + "' was not found. Type 'help' for help.");
                    return ProcessingResult.ERROR;
            }
        } catch (CommandUsageException exception){
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            Outputer.printLn("Usage: '" + command + "'");
            return ProcessingResult.ERROR;
        }
        return ProcessingResult.OK;
    }

    /**
     * Generates organization to add.
     *
     * @return Organization to add.
     * @throws IncorrectInputInScriptException When something went wrong in script.
     */
    private OrganizationRaw generateOrganizationAdd() throws IncorrectInputInScriptException {
        OrganizationAsker organizationAsker = new OrganizationAsker(userScanner);
        if (fileMode()) organizationAsker.setScriptMode();
        return new OrganizationRaw(
                organizationAsker.askName(),
                organizationAsker.askCoordinates(),
                organizationAsker.askAnnualTurnover(),
                organizationAsker.askEmployeesCount(),
                organizationAsker.askOrganizationType(),
                organizationAsker.askAddress()
        );
    }

    /**
     * Generates organization to update.
     *
     * @return Organization to update.
     * @throws IncorrectInputInScriptException When something went wrong in script.
     */
    private OrganizationRaw generateOrganizationUpdate() throws IncorrectInputInScriptException {
        OrganizationAsker organizationAsker = new OrganizationAsker(userScanner);
        if (fileMode()) organizationAsker.setScriptMode();
        String name = organizationAsker.askQuestion("Do you want to change the organization's name?") ?
                organizationAsker.askName() : null;
        Coordinates coordinates = organizationAsker.askQuestion("Do you want to change the coordinates of the organization?") ?
                organizationAsker.askCoordinates() : null;
        float annualTurnover = organizationAsker.askQuestion("Want to change the organization's annual turnover?") ?
                organizationAsker.askAnnualTurnover() : -1;
        Long employeesCount = organizationAsker.askQuestion("Do you want to change the number of employees in the organization?") ?
                organizationAsker.askEmployeesCount() : -1;
        OrganizationType organizationType = organizationAsker.askQuestion("Do you want to change the type of organization?") ?
                organizationAsker.askOrganizationType() : null;
        Address address = organizationAsker.askQuestion("Do you want to change the address of the organization?") ?
                organizationAsker.askAddress() : null;
        return new OrganizationRaw(
                name,
                coordinates,
                annualTurnover,
                employeesCount,
                organizationType,
                address
        );
    }

    /**
     * Checks if UserHandler is in file mode now.
     *
     * @return Is UserHandler in file mode now boolean.
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}