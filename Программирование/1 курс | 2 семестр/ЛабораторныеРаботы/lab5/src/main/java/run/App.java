package run;

import commands.*;
import managers.*;

import java.util.Scanner;

public class App {

    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";

    public static void main(String[] args) {

        String filename = "/Users/rossilman/Desktop/db.xml";
        try (Scanner userScanner = new Scanner(System.in)) {

            CollectionManager collectionManager = new CollectionManager();
            OrganizationAsker organizationAsker = new OrganizationAsker(collectionManager, userScanner);
            FileManager fileManager = new FileManager(filename);
            CommandManager commandManager = new CommandManager(
                    new AddCommand(collectionManager, organizationAsker),
                    new AddIfMaxCommand(), // TODO Not completed
                    new ClearCommand(collectionManager),
                    new ExecuteScriptCommand(), // TODO Not completed
                    new ExitCommand(),
                    new HistoryCommand(), // TODO Not completed
                    new InfoCommand(collectionManager),
                    new PrintAscendingCommand(collectionManager),
                    new PrintDescendingCommand(collectionManager),
                    new PrintFieldDescendingTypeCommand(), // TODO Not completed
                    new RemoveAtCommand(collectionManager),
                    new RemoveByIdCommand(collectionManager),
                    new SaveCommand(fileManager, collectionManager),
                    new ShowCommand(collectionManager),
                    new ShuffleCommand(collectionManager),
                    new UpdateCommand(collectionManager, organizationAsker),
                    new HelpCommand()

            );
            Console console = new Console(collectionManager, commandManager, userScanner, organizationAsker, fileManager);
            console.interactiveMode();
        }
    }
}
