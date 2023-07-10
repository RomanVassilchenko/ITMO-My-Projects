package NetInteraction;

import Application.Commander;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class SelectorManager {
    public static final Logger logger = Logger.getLogger(Commander.class.getName());
    private final ServerManager serverManager;
    private final Selector selector;
    final DatabaseManager dataBaseManager;

    final Commander commander;
    public SelectorManager(ServerManager serverManager, Selector selector)
            throws IOException {
        this.serverManager = serverManager;
        this.selector = selector;
        commander = ServerEvents.getCommander();
        dataBaseManager = commander.getDataBaseManager();

        Handler handler = new FileHandler("Server"+ File.separator+"logs"+File.separator+"log6.txt");
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    public void run() throws Exception {
        while (true) {
            if (selector.selectNow() == 0) {
                continue;
            }
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();
                if (key.isValid()) {
                    if (key.isReadable()) {
                        serverManager.receiveCommand(key);
                        serverManager.handle();
                    } else if (key.isWritable()) {
                        serverManager.sendToClient(key);
                    }
                }
                keyIter.remove();
            }

        }
    }
}
