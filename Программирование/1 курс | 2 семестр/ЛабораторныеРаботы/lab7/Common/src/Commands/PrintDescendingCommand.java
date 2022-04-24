package Commands;

import Data.Organizations;
import Data.User;

import java.util.Arrays;
import java.util.Collections;

public class PrintDescendingCommand extends Command{
    public PrintDescendingCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase dataBase, Organizations organizations, Object... args) {
        try {
            validate(args);
            if(user != null){
                if (organizations.isEmpty()) {
                    return "The collection is empty. There is no data inside the file!";
                } else {
                    Object[] out = organizations.toArray();
                    Arrays.sort(out, Collections.reverseOrder());
                    StringBuilder builder = new StringBuilder();
                    for (Object o : out) {
                        builder.append(o).append("\n");
                    }
                    return builder.toString();
                }
            }
            return "You are not logged in";
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
