package NetInteraction;
import Dependency.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;


public class Answer{
    SocketAddress addr;
    DatagramSocket sock;

    public Answer(DatagramSocket sock,SocketAddress addr) {
        this.sock = sock;
        this.addr = addr;
    }

    public void answerForServer(Command command) throws IOException {
        //Отправляем данные серверу
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(command);
        byte[] commandArray = outputStream.toByteArray();
        DatagramPacket dp = new DatagramPacket(commandArray, commandArray.length, addr);
        sock.send(dp);
    }
}
