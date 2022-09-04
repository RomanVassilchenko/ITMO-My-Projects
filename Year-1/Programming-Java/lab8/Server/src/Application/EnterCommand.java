package Application;

import Dependency.Command;

import java.io.IOException;
import java.sql.SQLException;

public class EnterCommand implements Runnable {
    private final Command commandFromClient;
    private final Commander commander;
    public EnterCommand(Command commandFromClient,Commander commander){
        this.commandFromClient = commandFromClient;
        this.commander = commander;
    }


    @Override
    public void run() {
        Command commandForClient =null;
        try {
           commandForClient= commander.interactiveMod(commandFromClient);
           commander.getSend().add(commandForClient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
