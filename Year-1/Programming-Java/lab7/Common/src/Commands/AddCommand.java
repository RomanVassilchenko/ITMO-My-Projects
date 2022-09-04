package Commands;

import Data.Organization;
import Data.Organizations;
import Data.User;
import Exceptions.NotDatabaseUpdateException;
import Exceptions.UserNotFoundException;

import java.sql.SQLException;
import java.util.Scanner;

public class AddCommand extends Command implements Fillable{

    public AddCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase dataBase, Organizations organizations, Object... args) {
        try {
            validate(args);
            if(user != null){
                int userID = dataBase.selectUserID(user.getLogin(), user.getPassword());
                dataBase.insert(((Organization) args[0]), userID);
                CommandManager.updateCollection();
                return "\nItem added to collection";
            }
            return "You are not logged in";
        } catch (IllegalArgumentException e){
            return "Error in arguments!";
        } catch (SQLException | NotDatabaseUpdateException e){
            return "Error in database! Please try again later!";
        } catch (UserNotFoundException e){
            return "Error with user's data! Please auth again!";
        }
    }

    @Override
    public Object[] fill(Scanner scanner) {
        Object[] args = new Object[1];
        args[0] = Organization.fillOrganization(scanner);
        return args;
    }
}
