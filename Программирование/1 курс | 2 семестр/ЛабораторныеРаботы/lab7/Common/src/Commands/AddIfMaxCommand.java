package Commands;

import Data.Organization;
import Data.Organizations;
import Data.User;
import Exceptions.NotDatabaseUpdateException;
import Exceptions.UserNotFoundException;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class AddIfMaxCommand extends Command implements Fillable{

    public AddIfMaxCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase dataBase, Organizations organizations, Object... args) {
        try {
            validate(args);
            if(user != null){
                int userID = dataBase.selectUserID(user.getLogin(), user.getPassword());
                Organization maxOrganization = Collections.max(organizations, new Comparator<Organization>() {
                    @Override
                    public int compare(Organization o1, Organization o2) {
                        int result = o1.getAnnualTurnOver().compareTo(o2.getAnnualTurnOver());
                        if(result == 0){
                            result = o1.getEmployeesCount().compareTo(o2.getEmployeesCount());
                        }
                        return result;
                    }
                });
                if(maxOrganization.getAnnualTurnOver() <= ((Organization) args[0]).getAnnualTurnOver()) {
                dataBase.insert(((Organization) args[0]), userID);
                CommandManager.updateCollection();
                return "\nItem added to collection";
                } else return "\nItem is less than the max value!";
            }
            return "You are not logged in";
        } catch (IllegalArgumentException | SQLException | UserNotFoundException | NotDatabaseUpdateException e) {
            return e.getMessage();
        }
    }

    @Override
    public Object[] fill(Scanner scanner) {
        Object[] args = new Object[1];
        args[0] = Organization.fillOrganization(scanner);
        return args;
    }
}
