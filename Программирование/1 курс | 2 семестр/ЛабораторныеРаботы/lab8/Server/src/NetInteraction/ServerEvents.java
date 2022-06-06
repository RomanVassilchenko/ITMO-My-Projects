package NetInteraction;

import Application.CollectionManager;
import Application.Commander;
import Application.ServerConsole;
import Dependency.Utils;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ServerEvents {
    int  port;
    private static Commander commander;
    private ServerManager serverManager;
    public void run() throws Exception {
        File file =new File("Server/config.txt");
        Scanner scanner = new Scanner(file);
        String scn = scanner.nextLine();
        String[] strings = scn.trim().split(" +");
        port = Integer.parseInt (strings[0]);
        SocketAddress address = new InetSocketAddress(port);
        DatagramChannel channel = DatagramChannel.open();
        Selector selector = Selector.open();
        channel.socket().bind(address);
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, new Client());
        DatabaseManager databaseManager = new DatabaseManager();
        commander = new Commander(databaseManager);
        serverManager = new ServerManager(channel,port,commander,databaseManager);
        commander.setServerManager(serverManager);
        commander.start();
        SelectorManager selectorManager = new SelectorManager(serverManager,selector);
        selectorManager.run();
    }

    public static Commander getCommander() {
        return commander;
    }
}
