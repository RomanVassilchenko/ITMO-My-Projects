package Application;
import Dependency.Utils;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ServerConsole extends Thread {
    CollectionManager manager;
    public static final Logger logger = Logger.getLogger(ServerConsole.class.getName());
    public ServerConsole(CollectionManager manager) throws IOException {
        this.manager = manager;
        Handler handler = new FileHandler("Server/logs/log3.txt");
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    public void runConsole() {
        String userCommand = "";
        while (true) {
            try {
                userCommand = Utils.scanner().nextLine();
                interactiveMod(userCommand);
            } catch (NoSuchElementException e) {
                logger.info("Вы завершили работу сервера.");
                System.out.println("Вы завершили работу сервера.");
                System.exit(0);

            }

        }

    }

    public void interactiveMod(String command) {
        String[] finalUserCommand = command.trim().split(" +", 2);
        switch (finalUserCommand[0]) {
            case "help":
                System.out.println("help : вывести справку по доступным командам\n" +
                        "save : сохраняет коллекцию по-указанному пути\n" +
                        "change_path : изменяет путь для сохранения коллекции\n" +
                        "exit : завершает работу сервера");
                break;
            case "save":
                manager.save();
                break;
            case "change_path":
                System.out.println("Текущий путь:" + manager.getSavePath());
                System.out.println("Введите путь для сохранения коллекции:");
                manager.setSavePath(Utils.scanner().nextLine());
                break;
            case "exit":
                try {
                    manager.save();
                }catch (NullPointerException e){
                    System.out.println("Коллекция пуста.");
                }
                logger.info("Вы завершили работу сервера.");
                System.out.println("Вы завершили работу сервера.");
                System.exit(0);
                break;
            default:
                System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                break;
        }
    }

    @Override
    public void run() {
        runConsole();
    }
}
