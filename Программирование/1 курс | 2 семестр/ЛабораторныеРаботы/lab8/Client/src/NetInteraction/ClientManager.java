package NetInteraction;

import Dependency.Command;
import Dependency.Utils;

import javax.swing.*;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class ClientManager {
    private Ask ask;
    private Answer answer;
    String login;
    String password;
    String userCommand = "";
    String conditionOfAuthorization;
    private Command collectionForTable;


    public ClientManager(Ask ask, Answer answer) {
        this.ask = ask;
        this.answer = answer;

    }

    public void printCollection(Command command, JTextArea outputPanel) throws IOException, ClassNotFoundException {
        command.getStrings().stream().forEach(x -> outputPanel.append(x + "\n"));
    }

    public Command receiveCommand() throws IOException, ClassNotFoundException {
        return ask.askForServer();
    }

    public void send(Command command) throws IOException {
        answer.answerForServer(command);
    }

    public String getLogin() {
        return login;
    }

    public void printString(Command command, JTextArea outputPanel) throws IOException, ClassNotFoundException {
        outputPanel.append(command.getUserCommand() + "\n");
    }

    private Command commandOut;
    private boolean isFirstCommand = false;

    public Command getCommandOut() {
        return commandOut;
    }

    public void setCommandOut(Command commandOut) {
        this.commandOut = commandOut;
        isFirstCommand = true;
    }


    public void setFirstCommand(boolean firstCommand) {
        isFirstCommand = firstCommand;
    }

    public Command interact(Command commandFromGUI) throws IOException, ClassNotFoundException {
        try {
            if (!isFirstCommand) {
                userCommand = commandFromGUI.getUserCommand();
                if (userCommand.equals("connect")) {
                    commandFromGUI.setUserCommand("");
                }
            } else {
                userCommand = commandOut.getUserCommand();
                if (userCommand.equals("connect")) {
                    commandFromGUI.setUserCommand("");
                }
                isFirstCommand = false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Вы принудительно завершили работу клиентского приложения.");
            System.exit(0);
        }
        commandOut = validate(commandFromGUI);
        send(commandOut);
        Command commandIn = receiveCommand();
        if (commandIn.getUserCommand().equals("not reg")) {
            login = null;
            password = null;
            conditionOfAuthorization = "not reg";
            commandIn.setUserCommand("Пользаваетль с таким логином уже создан! Для регистрации введите reg, для авторизации auth:");
        } else if (commandIn.getUserCommand().equals("not auth")) {
            conditionOfAuthorization = "not auth";
            commandIn.setUserCommand("Вы не авторизованы! Для регистрации введите reg, для авторизации auth:");
        } else if (commandIn.getUserCommand().equals("getCollectionForTable")) {
            collectionForTable = commandIn;
        } else {
            conditionOfAuthorization = "auth";
        }
        return commandIn;
    }

    public Command getCollectionForTable() {
        return collectionForTable;
    }

    public Command validate(Command commandFromGUI) {
        Command commandForServer = new Command("", login, password);
        String[] finalUserCommand = commandFromGUI.getUserCommand().trim().split(" +", 3);
        switch (finalUserCommand[0]) {
            case "auth":
            case "reg":
                try {
                    if (finalUserCommand.length != 1)
                        throw new IndexOutOfBoundsException();
                    login = commandFromGUI.getLogin();
                    password = Utils.encryptThisString(commandFromGUI.getPassword());
                    commandForServer = new Command(finalUserCommand[0], login, password);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Вы ввели команду не правильно.");
                }
                break;
            case "insert":
                try {
                    if (finalUserCommand.length < 2 | finalUserCommand.length > 2)
                        throw new IndexOutOfBoundsException();
                    commandFromGUI.setLogin(login);
                    commandFromGUI.setPassword(password);
                    commandForServer = commandFromGUI;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Вы ввели команду не правильно.");
                } catch (NullPointerException e) {
                    System.err.println("Ключ должен быть целым число");
                }
                break;
            case "update":
                try {
                    if (finalUserCommand.length < 3 | finalUserCommand.length > 3)
                        throw new IndexOutOfBoundsException();
                    commandFromGUI.setLogin(login);
                    commandFromGUI.setPassword(password);
                    commandForServer = commandFromGUI;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Вы ввели команду не  правильно.");
                } catch (NullPointerException e) {
                    System.err.println("ID должно быть целым число");}
                break;
            case "remove_key":
                try {
                    if (finalUserCommand.length != 2) throw new IndexOutOfBoundsException();
                 commandForServer = new Command(userCommand, login, password);
                    break;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Вы ввели команду не правильно.");
                } catch (NullPointerException e) {
                    System.err.println("Ключ должен быть целым число");
                }
            case "remove_greater":
                try {
                    if (finalUserCommand.length != 2)
                        throw new IndexOutOfBoundsException();
                    commandForServer = new Command(userCommand, login, password);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Вы ввели команду не правильно.");
                } catch (NullPointerException e) {
                    System.err.println("Зарплата должна быть целым число");
                }
                break;
            case "replace_if_greater":
                try {
                    if (finalUserCommand.length < 3 | finalUserCommand.length > 3)
                        throw new IndexOutOfBoundsException();
                    commandForServer = new Command(userCommand, login, password);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Вы ввели команду не правильно.");
                } catch (NullPointerException e) {
                    System.err.println("Проверьте ключ или зарплату они дожны быть целыми числами");
                }
                break;
            case "remove_greater_key":
                try {
                    if (finalUserCommand.length < 2 | finalUserCommand.length > 2)
                        throw new IndexOutOfBoundsException();
                    commandForServer = new Command(userCommand, login, password);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Вы ввели команду не правильно.");
                } catch (NullPointerException e) {
                    System.err.println("Ключ должен быть целым число");
                }
                break;
            case "execute_script":
                try {
                    if (finalUserCommand.length < 2 | finalUserCommand.length > 2)
                        throw new IndexOutOfBoundsException();
                    commandForServer = new Command(userCommand, login, password);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Команда введена неверно.");
                }
                break;
            case "exit":
                System.out.println("Вы завершили работу клиентского приложения.");
                System.exit(0);
                break;
            default:
                commandForServer = new Command(finalUserCommand[0], login, password);
                break;

        }
        return commandForServer;
    }

    public boolean setupPassport(Command command, String passportID) throws IOException, ClassNotFoundException {
        boolean isCorrectPassport = false;
        Command passport = new Command("check_passport", login, password);
        passport.setPassportID(passportID);
        send(passport);
        if (receiveCommand().getUserCommand().equals("correct")) {
            isCorrectPassport = true;
            command.setPassportID(passportID);
        }
        return isCorrectPassport;
    }

    public String getConditionOfAuthorization() {
        return conditionOfAuthorization;
    }
}
