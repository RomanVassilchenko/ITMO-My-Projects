import Answers.Answer;
import Answers.ErrorAnswer;
import Answers.OkAnswer;
import Answers.Request;
import Commands.*;
import Data.User;
import Exceptions.CommandAlreadyExistsException;
import Exceptions.NotFoundCommandException;
import Exceptions.RightException;
import Exceptions.SameIdException;


import javax.sound.midi.Receiver;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Answers.ErrorAnswer.logger;
import static Reader.CommandInitializer.initCommands;


public class Interpreter extends Thread {
    public static final Logger logger = Logger.getLogger(Receiver.class.getName());
    private FileHandler fileTxt;
    private Request request;
    private boolean hasNewPacket=false;
    private Sender sender;
    private DatagramSocket socket;
    private DatagramPacket da;

    public Interpreter(Sender sender,DatagramSocket socket, String sqlLogin, String sqlPassword) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, CommandAlreadyExistsException, RightException, IOException, SameIdException, ClassNotFoundException {

        //CommandManager manager = CommandManager.getInstance(System.getenv("SQL_LOGIN"), System.getenv("SQL_PASSWORD"));
        CommandManager manager = CommandManager.getInstance(sqlLogin, sqlPassword);
        manager = initCommands(manager);

        CommandManager.updateCollection();
        this.sender=sender;
        this.socket=socket;
    }

    public void putCommand(Request request,DatagramPacket da){
        this.request=request;
        this.hasNewPacket=true;
        this.da=da;
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            if (hasNewPacket){
                interpret(request,da);
            }
        }
    }

    public void interpret(Request request, DatagramPacket da) {
        hasNewPacket=false;
        User user = request.getUser();
        Command cmd = request.getCommand();
        Object[] args = request.getArgs();
        try{
            sender.send(new OkAnswer(CommandManager.execute(user,cmd, args)),da.getAddress(),da.getPort());

        } catch (NullPointerException e){
            sender.send(new ErrorAnswer("The server could not execute the command"), socket.getInetAddress(), socket.getPort());
        }
    }

    public void askCommand(Scanner scanner) throws IOException {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                String name = CommandManager.parseName(line);

                Object[] args = CommandManager.parseArgs(line);
                Command command = CommandManager.getCommand(name);
                Object[] fillableArg = CommandManager.getFillableArgs(command, scanner);
                args = CommandManager.concatArgs(args, fillableArg);

                CommandManager.validate(command, args);
                logger.info(CommandManager.execute(new User("server", "server"), command, args));
                logger.info("The team is failed");
            }
            catch (NoSuchElementException e){
                logger.severe("Well, why-you decided to Ctrl-D us :(");
                System.exit(0);
            }
            catch (NotFoundCommandException | IllegalArgumentException e) {
                logger.log(Level.SEVERE, "Command not found or wrong amount of arguments");
            }
        }
    }
}
