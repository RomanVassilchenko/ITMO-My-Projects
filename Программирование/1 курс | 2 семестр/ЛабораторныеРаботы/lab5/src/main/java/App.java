import commands.*;
import managers.*;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        try (Scanner userScanner = new Scanner(System.in)) {

            CollectionManager collectionManager = new CollectionManager();
            OrganizationAsker organizationAsker = new OrganizationAsker(collectionManager, userScanner);
            FileManager fileManager = new FileManager("/Users/rossilman/Desktop/db.xml");
            CommandManager commandManager = new CommandManager(
                    new AddCommand(collectionManager, organizationAsker),
                    null,
                    new ClearCommand(collectionManager),
                    null,
                    null,
                    null,
                    null,
                    new PrintAscendingCommand(collectionManager),
                    new PrintDescendingCommand(collectionManager),
                    null,
                    null,
                    null,
                    null,
                    null,
                    new ShuffleCommand(collectionManager),
                    null,
                    null

            );
            Console console = new Console(commandManager, userScanner, organizationAsker);
        }
    }
}
