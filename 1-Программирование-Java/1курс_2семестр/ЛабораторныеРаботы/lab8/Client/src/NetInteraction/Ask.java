package NetInteraction;

import Dependency.Command;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class Ask {
    final DatagramSocket sock;

    public Ask(DatagramSocket sock, SocketAddress address) {
        this.sock = sock;
    }

    public Command askForServer() throws IOException, ClassNotFoundException {
        //Получаем даssнные у сервера
        byte[] b = new byte[65536];
        DatagramPacket reply = new DatagramPacket(b, b.length);
        sock.receive(reply);
        byte[] data = reply.getData();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (Command) is.readObject();
    }

}
