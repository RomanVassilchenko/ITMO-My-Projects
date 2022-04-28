import Answers.Answer;
import Answers.BigDataAnswer;
import Answers.OkAnswer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

public class SenderTask implements Runnable {
    private final static int BID_DATA_CONST=7000;
    public static final Logger logger = Logger.getLogger(Receiver.class.getName());
    private DatagramSocket socket;
    private Answer answer;
    private InetAddress address;
    private int port;

    public SenderTask(DatagramSocket socket, Answer answer, InetAddress address, int port) {
        this.socket = socket;
        this.answer = answer;
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(answer);
            objectOutputStream.flush();

            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();

            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, address, port);
            try {
                if (bytes.length>(BID_DATA_CONST*2)) sendBigDataAnswer(bytes, address, port, answer);
                else {
                    socket.send(datagramPacket);
                    logger.info("A response has been sent to " + datagramPacket.getAddress() + ":" + datagramPacket.getPort());
                    answer.logAnswer();
                }
            } catch (SocketException | IllegalStateException e) {
                sendBigDataAnswer(bytes, address, port, answer);
            }
        } catch (IOException e) {
            logger.severe("Couldn't send a response");
        }
    }

    private void sendBigDataAnswer(byte[] bytes, InetAddress address, int port, Answer answer) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        logger.info("Too much data transferred");
        Answer bigDataAnswer = new BigDataAnswer();
        partSend(bigDataAnswer,address,port,objectOutputStream,byteArrayOutputStream);

        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        logger.info("Number of bytes: "+answer.getAnswer().getBytes().length);
        int count = (answer.getAnswer().getBytes().length / BID_DATA_CONST + 1);
        logger.info("The number of transmitted packets is "+count);
        Answer countAnswer = new OkAnswer(Integer.toString(count));
        partSend(countAnswer,address,port,objectOutputStream,byteArrayOutputStream);

        for (int i=0; i<count; i++){
            Answer newAnswer;
            if (i == count-1){
                logger.info("Number of bytes: "+answer.getAnswer().substring(i*BID_DATA_CONST).getBytes().length);
                newAnswer = new OkAnswer(answer.getAnswer().substring(i*BID_DATA_CONST));
            } else {
                logger.info("Number of bytes: "+answer.getAnswer().substring(i*BID_DATA_CONST, (i+1)*BID_DATA_CONST).getBytes().length);
                newAnswer = new OkAnswer(answer.getAnswer().substring(i*BID_DATA_CONST, (i+1)*BID_DATA_CONST));
            }
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            partSend(newAnswer,address,port,objectOutputStream,byteArrayOutputStream);

        }

    }

    private void partSend(Answer answer, InetAddress address, int port, ObjectOutputStream objectOutputStream, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        objectOutputStream.writeObject(answer);
        objectOutputStream.flush();
        byte[] newBytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        DatagramPacket datagramPacket = new DatagramPacket(newBytes, newBytes.length, address, port);
        socket.send(datagramPacket);
        logger.info("A response has been sent to " + datagramPacket.getAddress() + ":" + datagramPacket.getPort());
        answer.logAnswer();
        objectOutputStream.reset();
        byteArrayOutputStream.reset();
    }
}
