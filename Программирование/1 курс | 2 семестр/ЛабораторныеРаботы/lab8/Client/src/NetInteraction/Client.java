package NetInteraction;

import Dependency.Command;
import GUI.AuthorizationForm;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {
    public static void main(String args[]) {
        try {
            File file = new File("Client/config.txt");
            Scanner scanner = new Scanner(file);
            String scn = scanner.nextLine();
            String[] strings = scn.trim().split(" +");
            String serverAddress = strings[0];
            int serverPort = Integer.parseInt(strings[1]);
            DatagramSocket sock = new DatagramSocket();
            SocketAddress address = new InetSocketAddress(InetAddress.getByName(serverAddress), serverPort);
            sock.connect(address);
            Ask ask = new Ask(sock, address);
            Answer answer = new Answer(sock, address);

            ClientManager clientManager = new ClientManager(ask, answer);
            ClientEvents clientEvents = new ClientEvents(clientManager);
            AuthorizationForm authorizationForm = new AuthorizationForm(clientEvents);
            Command savedCommand;
            try {
                clientEvents.run();
            } catch (PortUnreachableException e) {
                    savedCommand = clientManager.getCommandOut();
                    System.out.println("А сервачек то отключен, через 3 секунды попробуем востановить соединение");
                    try {
                        Thread.sleep(1000 * 3);
                    } catch (InterruptedException interruptedException) {
                    }
                    try {
                        if (savedCommand != null) {
                            clientManager.setCommandOut(savedCommand);
                        }
                        clientEvents.run();
                    } catch (PortUnreachableException x) {
                        clientManager.setFirstCommand(false);
                    }
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}