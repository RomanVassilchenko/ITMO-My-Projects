import commands.AddCommand;
import commands.PrintAscendingCommand;
import managers.CollectionManager;
import managers.OrganizationAsker;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        try (Scanner userScanner = new Scanner(System.in)) {
            CollectionManager collectionManager = new CollectionManager();
            OrganizationAsker organizationAsker = new OrganizationAsker(collectionManager, userScanner);
            new AddCommand(collectionManager, organizationAsker).execute();
            new PrintAscendingCommand(collectionManager).execute();
        }
    }
}
