import Answers.Answer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Receiver extends Thread {
    private final DatagramChannel channel;
    private final SocketAddress serverAddress;
    private final ByteBuffer buffer;

    public Receiver(DatagramChannel channel, SocketAddress serverAddress) {
        this.channel = channel;
        this.serverAddress = serverAddress;
        this.buffer = ByteBuffer.allocate(16384);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                buffer.clear();
                channel.connect(serverAddress);

                channel.receive(buffer);
                buffer.flip();

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Answer answer = (Answer) objectInputStream.readObject();

                if (answer.getAnswer().equals("BigData")){
                    PrintConsole.println("Слишком большой объем данных. Ожидаемое количество пакетов:");
                    buffer.clear();
                    channel.receive(buffer);
                    buffer.flip();
                    byteArrayInputStream = new ByteArrayInputStream(buffer.array());
                    objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    Answer countAnswer = (Answer) objectInputStream.readObject();
                    countAnswer.printAnswer();
                    for( int i=0;i<Integer.parseInt(countAnswer.getAnswer()); i++){
                        buffer.clear();
                        channel.receive(buffer);
                        buffer.flip();
                        byteArrayInputStream = new ByteArrayInputStream(buffer.array());
                        objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        Answer newAnswer = (Answer) objectInputStream.readObject();
                        PrintConsole.print(newAnswer.getAnswer());
                    }
                } else {
                    answer.printAnswer();
                }

                objectInputStream.close();
                byteArrayInputStream.close();
                buffer.clear();
                channel.disconnect();
            } catch (PortUnreachableException | IllegalStateException e) {
                PrintConsole.println("Сервер не доступен");
                try {
                    channel.disconnect();
                } catch (IOException ex) {
                    PrintConsole.printerror("Произошла непредвиденная ошибка");
                }
            } catch (IOException | ClassNotFoundException e) {
                PrintConsole.printerror("Произошла непредвиденная ошибка");
            }
        }
    }

    @Override
    public synchronized void start() {
        this.setDaemon(true);
        super.start();
    }


}

