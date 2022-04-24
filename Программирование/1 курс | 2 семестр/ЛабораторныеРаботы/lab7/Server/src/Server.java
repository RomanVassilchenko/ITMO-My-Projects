import Exceptions.CommandAlreadyExistsException;
import Exceptions.RightException;
import Exceptions.SameIdException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.*;

public class Server {
    public static int SERVER_PORT = 12501;
    public static final Logger logger = Logger.getLogger(Server.class.getName());
    String source;
    private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    private static ThreadPoolExecutor executor;
    //FIXME НЕ ЗАБЫТЬ ПРЕКРУТИТЬ ЛОГИ ОБРАТНО
    public static void main(String[] args) throws SameIdException, InvocationTargetException, IllegalAccessException, InstantiationException, RightException, NoSuchMethodException, CommandAlreadyExistsException, IOException, ClassNotFoundException {
        String envVariable="";
        if (args.length==0){
            throw new ArrayIndexOutOfBoundsException();
        }
        if (args!=null || args.length!=0){
            envVariable=args[0];
        }
        Server server = new Server(envVariable);
        server.launch();
    }

    public Server(String source) throws IOException {
        this.source=source;
        fileTxt=new FileHandler(source);
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }

    public void launch() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, CommandAlreadyExistsException, SameIdException, RightException, IOException, ClassNotFoundException {
        DatagramSocket socket = setSocket();
        if (socket != null) {
            logger.info("The server is running");

            Scanner scanner = new Scanner(System.in);
            Sender sender = new Sender(socket,10);
            Interpreter interpreter = new Interpreter(sender,socket);
            interpreter.start();
            Receiver receiver = new Receiver(socket, interpreter);


            receiver.setDaemon(true);
            receiver.start();

            shutDownHook();
            while (true) {
                interpreter.askCommand(scanner);
            }
        }
    }

    public DatagramSocket setSocket() {
        try {
            return new DatagramSocket(SERVER_PORT);
        } catch (SocketException e) {
            e.printStackTrace();

        }
        return null;
    }

    private void shutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("The server is stopped");

        }));
    }
}
