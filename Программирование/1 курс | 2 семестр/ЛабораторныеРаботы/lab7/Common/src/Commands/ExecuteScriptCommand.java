package Commands;

import Data.Organizations;
import Data.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);

            if (user != null) {
                // TODO: Это тоже не до конца доделано
                File file = new File((String) args[0]);
                if (!file.exists()) return "The script does not exist";
                else if (file.exists() && !file.canRead()) return "The script cannot be read, check the file rights (read rights)";
                else if (file.exists() && !file.canExecute()) return "The script cannot be executed, check the file rights (execution rights)";
                else {
                    try {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine().trim();
                            List<String> collection = Arrays.asList(line.split(" "));
                            if (collection.get(0).equals("execute_script")) {
                                if (!organizations.isEmpty()) {
//							Command cmd = Command.getCommand(Command.parseName(collection.get(0)));
//							cmd.execute(collection.get(1));
                                } else {
                                    return "The collection is empty, the recursion has been interrupted";
                                }
                            } else {
//						Command cmd = Command.getCommand(Command.parseName(line));
//						String[] arg = Command.parseArgs(line);
//						cmd.execute(arg);
                            }
                        }
                        scanner.close();
                        return "The script was executed";
                    } catch (FileNotFoundException e) {
                        return "The script does not exist";
                    }
                }
            }
            return "You are not logged in";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

//		if (args.length != 1) {
//			System.err.println("В команде " + getName() + " должен быть 1 параметр");
//
//		} else {
//				File file = new File(args[0]);
//				if (!file.exists()) System.err.println("Скрипта не существует");
//				else if (file.exists() && !file.canRead()) System.err.println("Скрипт невозможно прочитать, проверьте права файла(права на чтение)");
//				else if (file.exists() && !file.canExecute()) System.err.println("Скрипт невозможно выполнить, проверьте права файла (права на выполнение)");
//				else {
//				Scanner scanner = new Scanner(file);
//				while (scanner.hasNextLine()) {
//					String line = scanner.nextLine().trim();
//					List<String> collection = Arrays.asList(line.split(" "));
//					if (collection.get(0).equals("execute_script")) {
//						if (!persons.isEmpty()) {
//							Command cmd = Command.getCommand(Command.parseName(collection.get(0)));
//							cmd.execute(collection.get(1));
//						} else System.err.println("Коллекция пустая, рекурсия прервалась");
//					} else {
//						Command cmd = Command.getCommand(Command.parseName(line));
//						String[] arg = Command.parseArgs(line);
//						cmd.execute(arg);
//					}
//				}
//				scanner.close();
//				}

    }

    @Override
    public void validate(Object... args) throws IllegalArgumentException {
        super.validate(args);
    }
}