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

            while(true){
                try{
                Console.print("Write a filename or path. Write \"skip\" if you want to skip autoload. Default filename: db.xml\n" + Console.PS2);
                String s = userScanner.nextLine();
                if(s.equals("")) {
                    Console.printLn("Using file " + filename);
                    collectionManager.setCollection(fileManager.readCollection());
                    break;
                }
                if(s.equals("skip")) break;

                fileManager.setFilename(s);
                if(fileManager.readCollection().size() != 0){
                    filename = s;
                    Console.printLn("Using file " + filename);
                    collectionManager.setCollection(fileManager.readCollection());
                }
                } catch (NoSuchElementException e) {
                    Console.printError("The filename can't be loaded or recognized");
                    if(!userScanner.hasNext()) {
                        Console.printError("Ctrl-D Caused exit!");
                        System.exit(0);
                    }
                } catch (IllegalStateException e) {
                    Console.printError("Unexpected error!");
                    System.exit(0);
                }
            }
            Console console = new Console(commandManager, userScanner, organizationAsker);
            console.interactiveMode();
        }
    }
}
