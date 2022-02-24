package managers;

import collections.Address;
import collections.Coordinates;
import collections.OrganizationType;
import exceptions.MustBeNotEmptyException;
import exceptions.NotInDeclaredLimitsException;
import run.App;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Asks user the organization's information and returns it as an object
 */
public class OrganizationAsker {
    CollectionManager collectionManager;
    Scanner userScanner;

    public OrganizationAsker(CollectionManager collectionManager, Scanner userScanner) {
        this.userScanner = userScanner;
        this.collectionManager = collectionManager;
    }

    /**
     * Set unique id to the organization
     *
     * @return unique id of organization
     */
    public int setId() {
        return collectionManager.generateNewIdForCollection();
    }

    /**
     * Asks user the organization's name
     *
     * @return Organization's name
     * @throws MustBeNotEmptyException if the name is empty
     * @throws NoSuchElementException  if the name can't be recognized
     * @throws IllegalStateException   if something goes wrong
     */
    public String askName() {
        String name;
        while (true) {
            Console.print("Enter name:");
            Console.print(App.PS2);
            try {
                name = userScanner.nextLine().trim();
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (MustBeNotEmptyException e) {
                Console.printError("The name can't be empty");
            } catch (NoSuchElementException e) {
                Console.printError("The name can't be loaded or recognized");
            } catch (IllegalStateException e) {
                Console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return name;
    }

    /**
     * Asks user the organization's X axis
     *
     * @return x coordinate of organization
     * @throws NoSuchElementException if x coordinate of organization can't be recognized
     * @throws NumberFormatException  if x coordinate of organization is not an integer
     * @throws NullPointerException   if something goes wrong
     * @throws IllegalStateException  if something goes wrong
     */
    private int askX() {
        int x;
        while (true) {
            try {
                Console.print("Enter Coordinate X:");
                Console.print(App.PS2);
                String s = userScanner.nextLine().trim();
                x = Integer.parseInt(s);
                break;
            } catch (NoSuchElementException e) {
                Console.printError("The X axis can't be loaded or recognized");
            } catch (NumberFormatException e) {
                Console.printError("The X axis have to be an Integer value");
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Asks user the organization's Y axis
     *
     * @return y coordinate of organization
     * @throws NoSuchElementException if y coordinate of organization can't be recognized
     * @throws NumberFormatException  if y coordinate of organization is not a float
     * @throws NullPointerException   if something goes wrong
     * @throws IllegalStateException  if something goes wrong
     */
    private Float askY() {
        float y;
        while (true) {
            try {
                Console.print("Enter Coordinate Y:");
                Console.print(App.PS2);
                String s = userScanner.nextLine().trim();
                if (s.equals("")) throw new MustBeNotEmptyException();
                y = Float.parseFloat(s);
                break;
            } catch (NoSuchElementException e) {
                Console.printError("The Y axis can't be loaded or recognized");
            } catch (NumberFormatException e) {
                Console.printError("The Y axis have to be an Float value");
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            } catch (MustBeNotEmptyException e) {
                Console.printError("The Y axis can't be empty");
            }
        }
        return y;
    }

    /**
     * Asks user the organization's coordinates
     *
     * @return Organization's coordinates
     */
    public Coordinates askCoordinates() {
        int x;
        Float y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }


    /**
     * Asks user the organization's creation date
     *
     * @return Organization's creation date
     * @throws DateTimeException if something goes wrong with data
     */
    public LocalDateTime askCreationDate() {
        while (true) {
            try {
                return LocalDateTime.now();
            } catch (DateTimeException e) {
                Console.printError("Problem with local data");
            }
        }
    }

    /**
     * Asks user the organization's annual turnover
     *
     * @return annual turnover of organization
     * @throws NoSuchElementException if annual turnover of organization can't be recognized
     * @throws NumberFormatException  if annual turnover of organization is not a float
     * @throws NullPointerException   if something goes wrong
     * @throws IllegalStateException  if something goes wrong
     */
    public float askAnnualTurnover() {
        float turnOver;
        while (true) {
            try {
                Console.print("Enter Annual Turnover:");
                Console.print(App.PS2);
                String s = userScanner.nextLine().trim();
                turnOver = Float.parseFloat(s);
                if(turnOver <= 0) throw  new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException e) {
                Console.printError("The annual turnover can't be loaded or recognized");
            } catch (NumberFormatException e) {
                Console.printError("The annual turnover have to be an Float value");
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            } catch (NotInDeclaredLimitsException e) {
                Console.printError("Annual turnover should be positive and more than 0");
            }

        }
        return turnOver;
    }

    /**
     * Asks user the organization's employees count
     *
     * @return Organization's employees count
     * @throws NoSuchElementException if employees count of organization can't be recognized
     * @throws NumberFormatException  if employees count of organization is not a long
     * @throws NullPointerException   if something goes wrong
     * @throws IllegalStateException  if something goes wrong
     */
    public Long askEmployeesCount() {
        long employeesCount;
        while (true) {
            try {
                Console.print("Enter the amount of employees:");
                Console.print(App.PS2);
                String s = userScanner.nextLine().trim();
                employeesCount = Long.parseLong(s);
                if (employeesCount <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("The amount of employees can't be recognized");
            } catch (NotInDeclaredLimitsException exception) {
                Console.printError("The amount of employees should be positive and more than 0");
            } catch (NumberFormatException exception) {
                Console.printError("The amount of employees should be a long value");
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return employeesCount;
    }

    /**
     * Asks user the organization's type
     *
     * @return Organization's type
     * @throws NoSuchElementException   if type of organization can't be recognized
     * @throws IllegalArgumentException if there is no similar type in enum
     * @throws IllegalStateException    if something goes wrong
     */
    public OrganizationType askOrganizationType() {
        OrganizationType organizationType;
        while (true) {
            try {
                Console.printLn("Categories: " + OrganizationType.nameList());
                Console.print("Enter the organization type: ");
                Console.print(App.PS2);
                String s = userScanner.nextLine().trim();
                if(s.equals("")) return null;
                organizationType = OrganizationType.valueOf(s.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("Type can't be recognized");
            } catch (IllegalArgumentException exception) {
                Console.printError("There is no similar type in category");
            } catch (IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return organizationType;
    }

    /**
     * Asks user the organization's street
     *
     * @return Organization's street
     * @throws NoSuchElementException if street of organization can't be recognized
     * @throws IllegalStateException  if something goes wrong
     */
    private String askStreet() {
        String street;
        while (true) {
            try {
                Console.print("Enter street:");
                Console.print(App.PS2);
                street = userScanner.nextLine().trim();
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("Street can't be recognized");
            } catch (IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return street;
    }

    /**
     * Asks user the organization's zipcode
     *
     * @return Organization's zipcode
     * @throws NoSuchElementException if street of organization can't be recognized
     * @throws IllegalStateException  if something goes wrong
     */
    private String askZipCode() {
        String zipCode;
        while (true) {
            try {
                Console.print("Enter zipCode:");
                Console.print(App.PS2);
                zipCode = userScanner.nextLine().trim();
                if(zipCode.length() > 22) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("zipcode can't be recognized");
            } catch (IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            } catch (NotInDeclaredLimitsException e) {
                Console.printError("zipcode length can't be more than 22");
            }

        }
        return zipCode;
    }

    /**
     * Asks user the organization's address
     *
     * @return Organization's address
     */
    public Address askAddress() {
        String street = askStreet();
        String zipCode = askZipCode();
        if(street.equals("") && zipCode.equals("")) return null;
        return new Address(street, zipCode);
    }

}
