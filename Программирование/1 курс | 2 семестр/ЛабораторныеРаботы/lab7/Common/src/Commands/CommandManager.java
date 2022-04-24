package Commands;


import Data.Organization;
import Data.Organizations;
import Data.User;
import Exceptions.CommandAlreadyExistsException;
import Exceptions.NotDatabaseUpdateException;
import Exceptions.NotFoundCommandException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

public final class CommandManager {
    private static CommandManager instance;
    private static Organizations organizations;
    private static ArrayList<Command> commands;
    private static User user;
    private static DataBase database;

    public CommandManager() {
    }

    private CommandManager(String login, String password) throws ClassNotFoundException {
        database = new DataBase(login, password);
        organizations =new Organizations();
    }

    public static CommandManager getInstance(String login, String password) throws ClassNotFoundException {
        if (instance == null) {
            commands = new ArrayList<>();
            instance = new CommandManager(login,password);
        }

        return instance;
    }
    public static CommandManager getInstance() {
        if (instance == null) {
            commands = new ArrayList<>();
            instance = new CommandManager();
        }

        return instance;
    }

    public void initCommand(Class<? extends Command> clazz, String name, String description, Class<?>... argsTypes) throws CommandAlreadyExistsException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Command command : commands) {
            if (command.getName().equals(name)) {
                throw new CommandAlreadyExistsException("A command named " + name + " already exists");
            }
        }

        Command command = clazz.getConstructor(String.class, String.class, Class[].class)
                .newInstance(name, description, argsTypes);
        commands.add(command);
    }


    public static String[] parseArgs(String args) {
        String[] splitted = args.split(" ", 2);
        return splitted.length > 1 ? splitted[1].split(" ") : new String[]{};
    }
    public static String parseName(String command) {
        return command.split(" ")[0];
    }

    public static Command getCommand(String name) throws NotFoundCommandException {
        for (Command cmd : commands) {
            if (cmd.getName().equals(name)) {
                return cmd;
            }
        }
        throw new NotFoundCommandException("Command '" + name + "'was not initialized.");
    }
    public static void updateCollection() {
        try {
            Stack<Organization> updated = database.selectAllNotes();
            organizations.update(updated);
        } catch (SQLException  | NotDatabaseUpdateException e) {
            e.printStackTrace();
        }
    }

    public static String getCommandsInfo() {
        StringBuilder builder = new StringBuilder();
        for (Command cmd : commands) {
            builder.append(cmd.toString()).append("\n");
        }

        return builder.toString().trim();
    }

    public static User getUser() {
        return user;
    }

    public static String execute(User user, Command command, Object[] args) {
        return command.execute(user,database,organizations, args);

    }

    public static String execute(User user,String commandName, Object[] args) throws NotFoundCommandException {
        Command command = getCommand(commandName);
        return command.execute(user,database,organizations, args);
    }

    public static Object[] getFillableArgs(Command command, Scanner scanner) {
        if (command instanceof Fillable) {
            return ((Fillable) command).fill(scanner);
        }
        return new Object[]{};
    }

    public static Object[] concatArgs(Object[] args, Object[] fillableArgs) {
        return Stream.concat(Arrays.stream(args), Arrays.stream(fillableArgs)).toArray(Object[]::new);
    }

    public static void validate(Command command, Object[] args) {
        command.validate(args);
    }

    public static void validate(String commandName, Object[] args) throws NotFoundCommandException {
        Command command = getCommand(commandName);
        command.validate(args);
    }
    public static void auth(User user) {
        CommandManager.user = user;
    }
}
