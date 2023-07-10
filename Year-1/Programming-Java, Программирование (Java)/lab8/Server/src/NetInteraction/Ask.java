package NetInteraction;

import Application.Commander;
import Dependency.Command;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class Ask implements Callable<Command> {

    public static final Logger logger = Logger.getLogger(Ask.class.getName());
    private final SelectionKey key;

    public Ask(SelectionKey key, Commander commander) {
        this.key = key;
    }

    @Override
    public Command call() {
        //Получаем данные у клиента
        byte[] b = new byte[65536];
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.clear();
        DatagramChannel channel = (DatagramChannel) key.channel();
        Client client = (Client) key.attachment();
        try {
            client.clientAddress = channel.receive(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream in = new ByteArrayInputStream(buffer.array());
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Command command = null;
        try {
            command = (Command) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (command.isCollection()) {
            StringBuilder str = new StringBuilder();
            for (String string : command.getStrings()) {
                str.append(string).append("\n");
            }
            logger.info("Сообщение, получено: " + str + " - Адрес клиента:" + client.clientAddress);
        } else if (command.getUserCommand().equals("connect")) {
            logger.info("Новое подключение - Адрес клиента:" + client.clientAddress);
        } else {
            logger.info("Сообщение, получено: " + command.getUserCommand() + " - Адрес клиента:" + client.clientAddress);
        }
        key.interestOps(SelectionKey.OP_WRITE);
        return command;

    }
}
