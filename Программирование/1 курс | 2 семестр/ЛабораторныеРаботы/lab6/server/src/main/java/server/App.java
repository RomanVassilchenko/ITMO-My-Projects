package server;

import server.commands.*;
import server.utility.CollectionFileManager;
import server.utility.CollectionManager;
import server.utility.CommandManager;
import server.utility.RequestHandler;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App {

    public static final int PORT = 1821;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static Logger logger = LogManager.getLogManager().getLogger("ServerLogger");


    public static void main(String[] args) {
        String filename = "organizations.xml";
        if(args.length == 1) filename = args[0];
        CollectionFileManager collectionFileManager = new CollectionFileManager(filename);
        CollectionManager collectionManager = new CollectionManager(collectionFileManager);
        CommandManager commandManager = new CommandManager(
                new AddCommand(collectionManager),
                new AddIfMaxCommand(collectionManager),
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
                new SaveCommand(collectionManager),
                new ShowCommand(collectionManager),
                new ShuffleCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new HelpCommand(),
                new ServerExitCommand()
        );
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }
}
