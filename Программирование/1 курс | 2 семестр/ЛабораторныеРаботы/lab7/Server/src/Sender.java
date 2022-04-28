import Answers.Answer;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ForkJoinPool;

public class Sender  {
    private ForkJoinPool pool;
    private DatagramSocket socket;

    public Sender(DatagramSocket socket, int nThreads) {
        pool = new ForkJoinPool(nThreads);
        this.socket = socket;
    }

    public void send(Answer answer, InetAddress address, int port) {
        SenderTask task = new SenderTask(socket, answer, address, port);
        pool.execute(task);
    }
}
