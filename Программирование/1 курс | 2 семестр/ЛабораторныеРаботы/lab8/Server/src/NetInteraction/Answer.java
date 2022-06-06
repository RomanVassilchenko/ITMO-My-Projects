package NetInteraction;

import Application.Commander;
import Dependency.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Answer implements Runnable {
    DatagramChannel channel;
    Command command;
    SelectionKey key;
    public static final Logger logger = Logger.getLogger(Answer.class.getName());

    public Answer(DatagramChannel channel, Command command, SelectionKey key) throws IOException {
        this.channel = channel;
        this.command = command;
        this.key = key;
        /*Handler handler = new FileHandler("Server/logs/log1.txt");
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);*/
    }


    @Override
    public void run() {
        //Отправляем данные клиенту
        DatagramChannel channel = (DatagramChannel) key.channel();
        Client client = (Client) key.attachment();
        client.buffer.flip(); // Prepare buffer for sending
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.buffer = ByteBuffer.wrap(outputStream.toByteArray());
        int bytesSent = 0;
        try {
            bytesSent = channel.send(client.buffer, client.clientAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (command.isCollection()) {
            StringBuilder str = new StringBuilder();
            for (String string : command.getStrings()) {
                str.append(string).append("\n");
            }
            logger.info("Сообщение, отпрвлено: " + str.toString() + " - Адрес клиента:" + client.clientAddress);
        } else {
            logger.info("Сообщение, отпрвлено: " + command.getUserCommand() + " - Адрес клиента:" + client.clientAddress);
        }

        if (bytesSent != 0) { // Buffer completely written?
            // No longer interested in writes
            key.interestOps(SelectionKey.OP_READ);
        }
    }
}
