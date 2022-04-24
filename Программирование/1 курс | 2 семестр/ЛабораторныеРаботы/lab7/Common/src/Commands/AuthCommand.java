package Commands;

import Data.Organizations;
import Data.User;
import Exceptions.NotFoundCommandException;
import Exceptions.UserNotFoundException;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthCommand extends Command implements Fillable {
    public AuthCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);
            User authUser = (User)args[0];
            db.selectUserID(authUser.getLogin(), authUser.getPassword());
            CommandManager.auth(authUser);
            return "You have successfully logged in as " + authUser.getLogin();
        } catch (SQLException | UserNotFoundException
                e) {
            return e.getMessage();
        }
    }

    @Override
    public Object[] fill(Scanner scanner) {
        Object[] args = new Object[1];
        args[0] = User.fillUser(scanner);
        return args;
    }
}