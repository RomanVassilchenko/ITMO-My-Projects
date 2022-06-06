package Application;

import Dependency.Command;
import Dependency.Utils;
import NetInteraction.*;
import org.postgresql.util.PSQLException;

import java.io.*;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Класс обработчик команд
 */
public class Commander {
    private CollectionManager manager;
    private String[] finalUserCommand;
    private Command commandForClient;
    private DatabaseManager dataBaseManager;
    private ServerManager serverManager;
    public static final Logger logger = Logger.getLogger(Commander.class.getName());
    private static ConcurrentLinkedQueue<Command> executor = new ConcurrentLinkedQueue<>();
    private static ConcurrentLinkedQueue<Command> send = new ConcurrentLinkedQueue<>();

    /**
     * Конструктор который дает Commander manager для выполнения команд
     *
     * @param
     */
    public Commander(DatabaseManager dataBaseManager) throws IOException {
        this.dataBaseManager = dataBaseManager;
        manager = new CollectionManager(dataBaseManager);
        Handler handler = new FileHandler("Server/logs/log4.txt");
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    List<String> info = new ArrayList<String>();

    /**
     * Метод переводящий консоль в режим ввода команд
     *
     * @param
     */
    public Command interactiveMod(Command commandFromClient) throws IOException, ClassNotFoundException, SQLException {
        finalUserCommand = commandFromClient.getUserCommand().trim().split(" +", 3);
        switch (finalUserCommand[0]) {
            case "help":
                commandForClient = new Command(manager.help());
                break;
            case "info":
                commandForClient = new Command(manager.getInfoOfCollecttion());
                break;
            case "show":
                commandForClient = new Command(manager.show());
                break;
            case "insert":
                commandForClient = new Command(manager.insert(commandFromClient.getKey(), commandFromClient));
                break;
            case "update":
                commandForClient = new Command(manager.update(commandFromClient.getId(), commandFromClient));
                break;
            case "remove_key":
                commandForClient = new Command(manager.removeKey(Utils.integerConverter(finalUserCommand[1])
                        , commandFromClient));
                break;
            case "clear":
                commandForClient = new Command(manager.clear(commandFromClient));
                break;
            case "execute_script":
                commandForClient = new Command(executeScript(finalUserCommand[1], commandFromClient));
                break;
            case "remove_greater":
                commandForClient = new Command(manager.removeGreater(finalUserCommand[1], commandFromClient));
                break;
            case "replace_if_greater":
                commandForClient = new Command(manager.replaceIfGreater(Utils.integerConverter(finalUserCommand[1])
                        , Utils.integerConverter(finalUserCommand[2]), commandFromClient));
                break;
            case "remove_greater_key":
                commandForClient = new Command(manager.removeGreaterKey(Utils.integerConverter(finalUserCommand[1])
                        , commandFromClient));
                break;
            case "max_by_coordinates":
                commandForClient = new Command(manager.maxByCoordinates());
                break;
            case "print_descending":
                commandForClient = new Command(manager.getDescending());
                break;
            case "print_field_descending_position":
                commandForClient = new Command(manager.getFieldDescendingPosition());
                break;
            case "check_passport":
                if (manager.checkPassport(commandFromClient.getPassportID())) {
                    commandForClient = new Command("correct");
                } else {
                    commandForClient = new Command("not correct");
                }
                break;
            case "auth":
                commandForClient = new Command("Вы успешно авторизованы!");
                break;
            case "getCollectionForTable":
                commandForClient = new Command("getCollectionForTable",manager.getWorkerList());
                break;
            default:
                commandForClient = new Command("Неопознанная команда. Наберите 'help' для справки.");
                break;
        }
        return commandForClient;
    }

    /**
     * Запуск скрипта из интерактивного режима
     *
     * @param path путь, по-которому находится скрипт
     */
    public ArrayList<String> executeScript(String path, Command commandFromClient) throws IOException, SQLException {
        File file = new File(path);
        ArrayList<String> message = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String scn = scanner.nextLine();
                String[] finalUserCommand = scn.trim().split(" +", 3);
                if (finalUserCommand[0].equals("insert")) {
                    for (int i = 0; i < 9; i++) {
                        String scn1 = scanner.nextLine();
                        info.add(scn1);
                    }
                    ArrayList<String> answer = manager.insert(Utils.integerConverter(finalUserCommand[1])
                            , info, commandFromClient);
                    for (String string : answer) {
                        message.add(string);
                    }
                } else if (finalUserCommand[0].equals("update")) {
                    if (finalUserCommand[1].equals("coordinates")) {
                        for (int i = 0; i < 2; i++) {
                            String scn1 = scanner.nextLine();
                            info.add(scn1);
                        }
                        for (String string : manager.update(Utils.integerConverter(finalUserCommand[1])
                                , finalUserCommand[2], info, commandFromClient)) {
                            message.add(string);
                        }
                    } else if (finalUserCommand[1].equals("person")) {
                        for (int i = 0; i < 3; i++) {
                            String scn1 = scanner.nextLine();
                            info.add(scn1);
                        }
                        for (String string : manager.update(Utils.integerConverter(finalUserCommand[1])
                                , finalUserCommand[2], info, commandFromClient)) {
                            message.add(string);
                        }

                    } else {
                        String scn1 = scanner.nextLine();
                        info.add(scn1);
                        ArrayList<String> answer = manager.update(Utils.integerConverter(finalUserCommand[1])
                                , finalUserCommand[2], info, commandFromClient);
                        for (String string : answer) {
                            message.add(string);
                        }
                    }
                } else if (finalUserCommand[0].equals("save")) {
                    String scn1 = scanner.nextLine();
                    info.add(scn1);
                    message.add(manager.save(info));
                } else {
                    switch (finalUserCommand[0]) {
                        case "help":
                            message.add(manager.help());
                            break;
                        case "exit":
                            try {
                                manager.save();
                            } catch (NullPointerException e) {
                                System.out.println("Коллекция пуста.");
                            }
                            logger.info("Вы завершили работу сервера.");
                            System.out.println("Вы завершили работу сервера.");
                            System.exit(0);
                            break;
                        case "info":
                            message.add(manager.getInfoOfCollecttion());
                            break;
                        case "show":
                            for (String string : manager.show()) {
                                message.add(string);
                            }
                            break;
                        case "remove_key":
                            message.add(manager.removeKey(Utils.integerConverter(finalUserCommand[1]), commandFromClient));
                            break;
                        case "clear":
                            message.add(manager.clear(commandFromClient));
                            break;
                        case "execute_script":
                            for (String string : executeScript(finalUserCommand[1], commandFromClient)) {
                                message.add(string);
                            }
                            break;
                        case "remove_greater":
                            message.add(manager.removeGreater(finalUserCommand[1], commandFromClient));
                            break;
                        case "replace_if_greater":
                            message.add(manager.replaceIfGreater(Utils.integerConverter(finalUserCommand[1])
                                    , Utils.integerConverter(finalUserCommand[2]), commandFromClient));
                            break;
                        case "remove_greater_key":
                            message.add(manager.removeGreaterKey(Utils.integerConverter(finalUserCommand[1]), commandFromClient));
                            break;
                        case "max_by_coordinates":
                            message.add(manager.maxByCoordinates());
                            break;
                        case "print_descending":
                            for (String string : manager.getDescending()) {
                                message.add(string);
                            }
                            break;
                        case "print_field_descending_position":
                            for (String string : manager.getFieldDescendingPosition()) {
                                message.add(string);
                            }
                            break;

                        default:
                            message.add("Неопознанная команда. Наберите 'help' для справки.");
                            break;
                    }
                }
                info.clear();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            message.add("Неправильный путь или формат файла.");
        } catch (IOException e) {
            message.add("Ошибка доступа");
        }
        return message;
    }

    public void start() throws IOException, SQLException {
        logger.info("Сервер запущен c портом:" + serverManager.getPort());
        ServerConsole console = new ServerConsole(manager);
        console.start();
        logger.info(manager.loadDataBase());
    }

    public void checkIn(Command user) {
        if (!dataBaseManager.checkLogin(user)) {
            dataBaseManager.insertUser(user);
            send.add(new Command("Вы успешно зарегистрированы!"));
        } else {
            send.add(new Command("not reg"));
        }
    }

    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    public DatabaseManager getDataBaseManager() {
        return dataBaseManager;
    }

    public  ConcurrentLinkedQueue<Command> getSend() {
        return send;
    }

    public ConcurrentLinkedQueue<Command> getExecutor() {
        return executor;
    }

}