package NetInteraction;

import Application.Commander;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Scanner;

public class ServerEvents {
    int  port;
    private static Commander commander;

    public void run() throws Exception {
        File file =new File("Server" + File.separator+"config.txt");
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
        ServerManager serverManager = new ServerManager(channel, port, commander, databaseManager);
        commander.setServerManager(serverManager);
        commander.start();
        SelectorManager selectorManager = new SelectorManager(serverManager,selector);
        selectorManager.run();
    }

    public static Commander getCommander() {
        return commander;
    }
}
