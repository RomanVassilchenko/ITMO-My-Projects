package Commands;

import Data.Organizations;
import Data.User;
import Exceptions.UserNotFoundException;

import java.sql.SQLException;

public class ClearCommand extends Command {
    public ClearCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, Organizations organizations, Object... args) {
        try {
            validate(args);
            if (user!= null){
                int userID = db.selectUserID(user.getLogin(), user.getPassword());
                db.deleteUserNotes(userID);
                CommandManager.updateCollection();
                return "The collection has been cleared";
            }
            return "You are not logged in";
        } catch (IllegalArgumentException e){
            return "Error in arguments!";
        } catch (SQLException e){
            return "Error in database! Please try again later!";
        } catch (UserNotFoundException e){
            return "Error with user's data! Please auth again!";
        }

    }
}
