package NetInteraction;

import Application.Commander;
import Application.EnterCommand;
import Dependency.Command;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ServerManager {
    private final int port;
    private final DatagramChannel channel;
    private final Commander commander;
    private final DatabaseManager dataBaseManager;
    static final int numOfThreads = Runtime.getRuntime().availableProcessors();
    final ForkJoinPool pool = new ForkJoinPool(numOfThreads);
    final ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
    public ServerManager(DatagramChannel channel, int port,Commander commander,DatabaseManager databaseManager) {
        this.channel =channel;
        this.port = port;
        this.commander = commander;
        this.dataBaseManager = databaseManager;
    }

    public int getPort() {
        return port;
    }

    public void sendToClient(SelectionKey key) {
        while (!commander.getSend().isEmpty()) {
                pool.submit(new Answer(channel, commander.getSend().poll(), key));
        }
    }

    public void  handle(){
        while(!commander.getExecutor().isEmpty()) {
            Command command = commander.getExecutor().poll();
            if (command.getUserCommand().equals("connect")) {
                commander.getSend().add(new Command("Вы подключены к серверу!"));
                continue;
            }
            try {
                if (command.getUserCommand().equals("reg")) {
                    commander.checkIn(command);
                } else if (dataBaseManager.checkUserAuthorize(command)) {
                    pool.submit(new EnterCommand(command,commander));
                } else {
                    commander.getSend().add(new Command("not auth"));
                }
            } catch (NullPointerException | SQLException e) {
                commander.getSend().add(new Command("Коллекция пуста."));
                e.printStackTrace();
            }
        }
    }

    public void receiveCommand(SelectionKey key) throws ExecutionException, InterruptedException {
                commander.getExecutor().add(executorService.submit(new Ask(key, commander)).get());
    }



}
