package NetInteraction;

import Dependency.Command;

import javax.swing.*;
import java.io.IOException;


public class ClientEvents {
    ClientManager clientManager;
    Command commandIn;
    public ClientEvents(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public void run() throws IOException, ClassNotFoundException {
        clientManager.send(new Command("connect"));
        Command commandIn = clientManager.receiveCommand();
    }

    public void commandMode(Command command, JTextArea outputPanel) throws IOException, ClassNotFoundException {
        commandIn = clientManager.interact(command);
        if (commandIn.isCollection()) {
            clientManager.printCollection(commandIn,outputPanel);
        } else {
            clientManager.printString(commandIn,outputPanel);
        }
    }
    public void commandMode(Command command) throws IOException, ClassNotFoundException {
        commandIn = clientManager.interact(command);
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

}





