import commands.*;
import exceptions.IncorrectInputInScriptException;
import exceptions.MustBeNotEmptyException;
import managers.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        String filename = "db.xml";
        try (Scanner userScanner = new Scanner(System.in)) {

            CollectionManager collectionManager = new CollectionManager();
            OrganizationAsker organizationAsker = new OrganizationAsker(collectionManager, userScanner);
            FileManager fileManager = new FileManager(filename);
            CommandManager commandManager = new CommandManager(
                    new AddCommand(collectionManager, organizationAsker),
                    new AddIfMaxCommand(collectionManager, organizationAsker),
                    new ClearCommand(collectionManager),
                    new ExecuteScriptCommand(),
                    new ExitCommand(),
                    new HistoryCommand(),
                    new InfoCommand(collectionManager),
                    new PrintAscendingCommand(collectionManager),
                    new PrintDescendingCommand(collectionManager),
                    new PrintFieldDescendingTypeCommand(collectionManager),
                    new RemoveAtCommand(collectionManager),
                    new RemoveByIdCommand(collectionManager),
                    new SaveCommand(fileManager, collectionManager),
                    new ShowCommand(collectionManager),
                    new ShuffleCommand(collectionManager),
                    new UpdateCommand(collectionManager, organizationAsker),
                    new HelpCommand()

            );

            if(args.length == 0){
                Console.printLn("Using default filename: " + filename);
                collectionManager.setCollection(fileManager.readCollection());
            } else if(args.length > 1){
                Console.printError("More arguments than expected! (" + args.length  +", 1 expected)");
                commandManager.exit("");
            } else {
                fileManager.setFilename(args[0]);
                if(fileManager.readCollection().size() != 0){
                    filename = args[0];
                    Console.printLn("Using file " + filename);
                    collectionManager.setCollection(fileManager.readCollection());
                }
            }
            Console console = new Console(commandManager, userScanner, organizationAsker);
            console.interactiveMode();
        }
    }
}
