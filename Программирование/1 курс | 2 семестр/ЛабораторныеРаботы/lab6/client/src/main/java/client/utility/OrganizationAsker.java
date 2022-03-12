package client.utility;

import client.App;
import common.data.Address;
import common.data.Coordinates;
import common.data.OrganizationType;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;
import common.utility.Outputer;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is used to ask the user for input
 */
public class OrganizationAsker {
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

    public OrganizationAsker(Scanner userScanner) {
        this.userScanner = userScanner;
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
     * Ask the user for a name and return it
     * 
     * @return The name of the user.
     * @throws IncorrectInputInScriptException
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            Outputer.print("Enter name:");
            Outputer.print(App.PS2);
            try {
                name = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (MustBeNotEmptyException e) {
                Outputer.printError("The name can't be empty");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NoSuchElementException e) {
                Outputer.printError("The name can't be loaded or recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Ctrl-D Caused exit!");
                    System.exit(0);
                }
            } catch (IllegalStateException e) {
                Outputer.printError("Unexpected error!");
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
                Outputer.print("Enter Coordinate X:");
                Outputer.print(App.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(s);
                x = Integer.parseInt(s);
                break;
            } catch (NoSuchElementException e) {
                Outputer.printError("The X axis can't be loaded or recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Ctrl-D Caused exit!");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                Outputer.printError("The X axis have to be an Integer value");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printError("Unexpected error!");
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
                Outputer.print("Enter Coordinate Y:");
                Outputer.print(App.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(s);
                if (s.equals("")) throw new MustBeNotEmptyException();
                y = Float.parseFloat(s);
                break;
            } catch (NoSuchElementException e) {
                Outputer.printError("The Y axis can't be loaded or recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Ctrl-D Caused exit!");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                Outputer.printError("The Y axis have to be an Float value");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printError("Unexpected error!");
                System.exit(0);
            } catch (MustBeNotEmptyException e) {
                Outputer.printError("The Y axis can't be empty");
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
     * Ask the user to enter the annual turnover of the company
     * 
     * @return The annual turnover is being returned.
     * @throws IncorrectInputInScriptException
     */
    public float askAnnualTurnover() throws IncorrectInputInScriptException {
        float turnOver;
        while (true) {
            try {
                Outputer.print("Enter Annual Turnover:");
                Outputer.print(App.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(s);
                turnOver = Float.parseFloat(s);
                if(turnOver <= 0) throw  new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException e) {
                Outputer.printError("The annual turnover can't be loaded or recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Ctrl-D Caused exit!");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                Outputer.printError("The annual turnover have to be an Float value");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printError("Unexpected error!");
                System.exit(0);
            } catch (NotInDeclaredLimitsException e) {
                Outputer.printError("Annual turnover should be positive and more than 0");
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
                Outputer.print("Enter the amount of employees:");
                Outputer.print(App.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(s);
                employeesCount = Long.parseLong(s);
                if (employeesCount <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printError("The amount of employees can't be recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Ctrl-D Caused exit!");
                    System.exit(0);
                }
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printError("The amount of employees should be positive and more than 0");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printError("The amount of employees should be a long value");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printError("Unexpected error!");
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
                Outputer.printLn("Categories: " + OrganizationType.nameList());
                Outputer.print("Enter the organization type: ");
                Outputer.print(App.PS2);
                String s = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(s);
                if(s.equals("")) return null;
                organizationType = OrganizationType.valueOf(s.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printError("Type can't be recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Ctrl-D Caused exit!");
                    System.exit(0);
                }
            } catch (IllegalArgumentException exception) {
                Outputer.printError("There is no similar type in category");
                if (scriptMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printError("Unexpected error!");
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
                Outputer.print("Enter street:");
                Outputer.print(App.PS2);
                street = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(street);
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printError("Street can't be recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Ctrl-D Caused exit!");
                    System.exit(0);
                }
            } catch (IllegalStateException exception) {
                Outputer.printError("Unexpected error!");
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
                Outputer.print("Enter zipCode:");
                Outputer.print(App.PS2);
                zipCode = userScanner.nextLine().trim();
                if(scriptMode) Outputer.printLn(zipCode);
                if(zipCode.length() > 22) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printError("zipcode can't be recognized");
                if (scriptMode) throw new IncorrectInputInScriptException();
                if(!userScanner.hasNext()) {
                    Outputer.printError("Ctrl-D Caused exit!");
                    System.exit(0);
                }
            } catch (IllegalStateException exception) {
                Outputer.printError("Unexpected error!");
                System.exit(0);
            } catch (NotInDeclaredLimitsException e) {
                Outputer.printError("zipcode length can't be more than 22");
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
