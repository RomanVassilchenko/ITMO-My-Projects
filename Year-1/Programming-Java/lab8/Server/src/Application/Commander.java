package Application;

import Dependency.Command;
import Dependency.Utils;
import NetInteraction.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Класс обработчик команд
 */
public class Commander {
    private final CollectionManager manager;
    private final DatabaseManager dataBaseManager;
    private ServerManager serverManager;
    public static final Logger logger = Logger.getLogger(Commander.class.getName());
    private static final ConcurrentLinkedQueue<Command> executor = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<Command> send = new ConcurrentLinkedQueue<>();

    /**
     * Конструктор который дает Commander manager для выполнения команд
     *
     * @param
     */
    public Commander(DatabaseManager dataBaseManager) throws IOException {
        this.dataBaseManager = dataBaseManager;
        manager = new CollectionManager(dataBaseManager);
        Handler handler = new FileHandler("Server"+File.separator+"logs"+File.separator+"log4.txt");
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    final List<String> info = new ArrayList<>();

    /**
     * Метод переводящий консоль в режим ввода команд
     *
     * @param
     */
    public Command interactiveMod(Command commandFromClient) throws SQLException {
        String[] finalUserCommand = commandFromClient.getUserCommand().trim().split(" +", 3);
        Command commandForClient;
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
    public ArrayList<String> executeScript(String path, Command commandFromClient) throws SQLException {
        File file = new File(path);
        ArrayList<String> message = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String scn = scanner.nextLine();
                String[] finalUserCommand = scn.trim().split(" +", 3);
                switch (finalUserCommand[0]) {
                    case "insert":
                        for (int i = 0; i < 9; i++) {
                            String scn1 = scanner.nextLine();
                            info.add(scn1);
                        }
                        ArrayList<String> answer = manager.insert(Utils.integerConverter(finalUserCommand[1])
                                , info, commandFromClient);
                        message.addAll(answer);
                        break;
                    case "update":
                        if (finalUserCommand[1].equals("coordinates")) {
                            for (int i = 0; i < 2; i++) {
                                String scn1 = scanner.nextLine();
                                info.add(scn1);
                            }
                            message.addAll(manager.update(Utils.integerConverter(finalUserCommand[1])
                                    , finalUserCommand[2], info, commandFromClient));
                        } else if (finalUserCommand[1].equals("person")) {
                            for (int i = 0; i < 3; i++) {
                                String scn1 = scanner.nextLine();
                                info.add(scn1);
                            }
                            message.addAll(manager.update(Utils.integerConverter(finalUserCommand[1])
                                    , finalUserCommand[2], info, commandFromClient));

                        } else {
                            String scn1 = scanner.nextLine();
                            info.add(scn1);
                            answer = manager.update(Utils.integerConverter(finalUserCommand[1])
                                    , finalUserCommand[2], info, commandFromClient);
                            message.addAll(answer);
                        }
                        break;
                    case "save":
                        String scn1 = scanner.nextLine();
                        info.add(scn1);
                        message.add(manager.save(info));
                        break;
                    default:
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
                                message.addAll(manager.show());
                                break;
                            case "remove_key":
                                message.add(manager.removeKey(Utils.integerConverter(finalUserCommand[1]), commandFromClient));
                                break;
                            case "clear":
                                message.add(manager.clear(commandFromClient));
                                break;
                            case "execute_script":
                                message.addAll(executeScript(finalUserCommand[1], commandFromClient));
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
                                message.addAll(manager.getDescending());
                                break;
                            case "print_field_descending_position":
                                message.addAll(manager.getFieldDescendingPosition());
                                break;

                            default:
                                message.add("Неопознанная команда. Наберите 'help' для справки.");
                                break;
                        }
                        break;
                }
                info.clear();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            message.add("Неправильный путь или формат файла.");
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