package managers;

import collections.Address;
import collections.Coordinates;
import collections.OrganizationType;
import exceptions.IncorrectInputInScriptException;
import exceptions.MustBeNotEmptyException;
import exceptions.NotInDeclaredLimitsException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * This class is used to ask the user for input
 */
public class OrganizationAsker {
    CollectionManager collectionManager;
    Scanner userScanner;
    private boolean scriptMode;

    /**
     * This function returns a Scanner object that is used to read user input
     * 
     * @return The Scanner object that is created in the method.
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * This function sets the scanner object that will be used to read user input
     * 
     * @param userScanner The Scanner object that will be used to read user input.
     */
    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    public OrganizationAsker(CollectionManager collectionManager, Scanner userScanner) {
        this.userScanner = userScanner;
        this.collectionManager = collectionManager;
        scriptMode = false;
    }

    /**
     * This function is used to set the script mode to true
     */
    public void setScriptMode(){
        scriptMode = true;
    }
    /**
     * This function sets the scriptMode variable to false, which means that the user is in control of
     * the CLI
     */
    public void setUserMode(){
        scriptMode = false;
    }

    
    /**
     * This function generates a new id for a collection
     * 
     * @return The id of the new collection.
     */
    public int setId() {
        return collectionManager.generateNewIdForCollection();
    }

    
    /**
     * Ask the user for a name and return it
     * 
     * @return The name of the user.
     * @throws IncorrectInputInScriptException
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            Console.print("Enter name:");
            Console.print(Console.PS2);
            try {
                name = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (MustBeNotEmptyException e) {
                Console.printError("The name can't be empty");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NoSuchElementException e) {
                Console.printError("The name can't be loaded or recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException e) {
                Console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return name;
    }

    
    /**
     * Ask for the X coordinate of the point
     * 
     * @return The X axis value.
     * @throws IncorrectInputInScriptException
     */
    private int askX() throws IncorrectInputInScriptException {
        int x;
        while (true) {
            try {
                Console.print("Enter Coordinate X:");
                Console.print(Console.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(s);
                x = Integer.parseInt(s);
                break;
            } catch (NoSuchElementException e) {
                Console.printError("The X axis can't be loaded or recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException e) {
                Console.printError("The X axis have to be an Integer value");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return x;
    }

    
    /**
     * The function askY() asks the user to enter the Y coordinate of the point
     * 
     * @return The Y axis value.
     * @throws IncorrectInputInScriptException
     */
    private Float askY() throws IncorrectInputInScriptException {
        float y;
        while (true) {
            try {
                Console.print("Enter Coordinate Y:");
                Console.print(Console.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(s);
                if (s.equals("")) throw new MustBeNotEmptyException();
                y = Float.parseFloat(s);
                break;
            } catch (NoSuchElementException e) {
                Console.printError("The Y axis can't be loaded or recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException e) {
                Console.printError("The Y axis have to be an Float value");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            } catch (MustBeNotEmptyException e) {
                Console.printError("The Y axis can't be empty");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }
        }
        return y;
    }

    
    /**
     * AskCoordinates()
     * 
     * @return A Coordinates object.
     * @throws IncorrectInputInScriptException
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        int x;
        Float y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }


    
    /**
     * It enter a date and time.
     *
     * @return LocalDateTime.
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
     * Ask the user to enter the annual turnover of the company
     * 
     * @return The annual turnover is being returned.
     * @throws IncorrectInputInScriptException
     */
    public float askAnnualTurnover() throws IncorrectInputInScriptException {
        float turnOver;
        while (true) {
            try {
                Console.print("Enter Annual Turnover:");
                Console.print(Console.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(s);
                turnOver = Float.parseFloat(s);
                if(turnOver <= 0) throw  new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException e) {
                Console.printError("The annual turnover can't be loaded or recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException e) {
                Console.printError("The annual turnover have to be an Float value");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            } catch (NotInDeclaredLimitsException e) {
                Console.printError("Annual turnover should be positive and more than 0");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }

        }
        return turnOver;
    }

    
    /**
     * Ask the user to enter the amount of employees
     * 
     * @return The amount of employees.
     * @throws IncorrectInputInScriptException
     */
    public Long askEmployeesCount() throws IncorrectInputInScriptException {
        long employeesCount;
        while (true) {
            try {
                Console.print("Enter the amount of employees:");
                Console.print(Console.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(s);
                employeesCount = Long.parseLong(s);
                if (employeesCount <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("The amount of employees can't be recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printError("The amount of employees should be positive and more than 0");
                if (scriptMode) throw new IncorrectInputInScriptException();
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
     * The function asks the user to enter the type of the organization
     * 
     * @return OrganizationType
     * @throws IncorrectInputInScriptException
     */
    public OrganizationType askOrganizationType() throws IncorrectInputInScriptException {
        OrganizationType organizationType;
        while (true) {
            try {
                Console.printLn("Categories: " + OrganizationType.nameList());
                Console.print("Enter the organization type: ");
                Console.print(Console.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(s);
                if(s.equals("")) return null;
                organizationType = OrganizationType.valueOf(s.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("Type can't be recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printError("There is no similar type in category");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return organizationType;
    }

    
    /**
     * Ask for a street name and return it
     * 
     * @return The method returns a string.
     * @throws IncorrectInputInScriptException
     */
    private String askStreet() throws IncorrectInputInScriptException {
        String street;
        while (true) {
            try {
                Console.print("Enter street:");
                Console.print(Console.PS2);
                street = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(street);
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("Street can't be recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return street;
    }

    
    /**
     * Ask user for zip code and validate it
     * 
     * @return The method askZipCode() returns a String.
     * @throws IncorrectInputInScriptException
     */
    private String askZipCode() throws IncorrectInputInScriptException {
        String zipCode;
        while (true) {
            try {
                Console.print("Enter zipCode:");
                Console.print(Console.PS2);
                zipCode = userScanner.nextLine().trim();
                if(scriptMode) Console.printLn(zipCode);
                if(zipCode.length() > 22) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printError("zipcode can't be recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            } catch (NotInDeclaredLimitsException e) {
                Console.printError("zipcode length can't be more than 22");
                if (scriptMode) throw new IncorrectInputInScriptException();
            }

        }
        return zipCode;
    }

    
    /**
     * Ask the user for a street and a zip code, and return an Address object if the user entered
     * something
     * 
     * @return Address.
     * @throws IncorrectInputInScriptException
     */
    public Address askAddress() throws IncorrectInputInScriptException {
        String street = askStreet();
        String zipCode = askZipCode();
        if(street.equals("") && zipCode.equals("")) return null;
        return new Address(street, zipCode);
    }

}
