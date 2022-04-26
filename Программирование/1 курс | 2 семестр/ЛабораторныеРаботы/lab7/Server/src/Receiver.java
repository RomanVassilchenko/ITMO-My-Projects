
import java.net.DatagramSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.FileHandler;

public class Receiver extends Thread {
    private ThreadPoolExecutor executor;
    private DatagramSocket socket;
    private Interpreter interpreter;


    public Receiver(DatagramSocket socket, Interpreter interpreter) {
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.socket = socket;
        this.interpreter = interpreter;
    }

    @Override
    public void run() {
            ReceiverTask task = new ReceiverTask(socket, interpreter);
            executor.execute(task);
    }
}
