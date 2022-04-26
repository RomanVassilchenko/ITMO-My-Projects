import Answers.Answer;
import Answers.ErrorAnswer;
import Answers.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static Answers.ErrorAnswer.logger;


public class ReceiverTask implements Runnable {
    public static final Logger logger = Logger.getLogger(Receiver.class.getName());
    private DatagramSocket socket;
    private Interpreter interpreter;
    private FileHandler fileTxt;


    public ReceiverTask(DatagramSocket socket, Interpreter interpreter) {
        this.socket = socket;
        this.interpreter = interpreter;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                byte[] bytes = new byte[16384];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                socket.receive(datagramPacket);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

                Request request = (Request) objectInputStream.readObject();
                logger.info("Request received from" + datagramPacket.getAddress() + ":" +
                        datagramPacket.getPort() + " - " + request.getCommand().getName() + Arrays.toString(request.getArgs()));
                interpreter.putCommand(request,datagramPacket);
                byteArrayInputStream.close();
                objectInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                logger.severe( "Couldn't receive a request!");
            }
        }
    }
}