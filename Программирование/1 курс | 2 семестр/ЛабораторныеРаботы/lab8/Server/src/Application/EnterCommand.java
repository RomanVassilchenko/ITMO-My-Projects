package Application;

import Dependency.Command;
import NetInteraction.ServerEvents;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.*;

public class EnterCommand implements Runnable {
    private Command commandFromClient;
    private Commander commander;
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
