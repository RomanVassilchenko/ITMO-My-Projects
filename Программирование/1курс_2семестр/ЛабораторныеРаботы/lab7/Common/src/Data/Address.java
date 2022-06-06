package Data;

import Exceptions.FiledIncorrect;
import Reader.ConsoleReader;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

public class Address implements Serializable {
    private String street;
    private String zipCode;

    public Address(String street, String zipCode) throws FiledIncorrect {

        if(zipCode.length() >= 22) throw new FiledIncorrect("The length of the 'zipCode' parameter must be less than 22");
        this.street = street;
        this.zipCode = zipCode;
    }

    public static Address fillAddress(Scanner scanner) throws FiledIncorrect {
        System.out.println("Entering the Address object");
        String street = (String) ConsoleReader.conditionalRead(scanner, "Enter street: ", true, String::toString);
        String zipCode = (String) ConsoleReader.conditionalRead(scanner, "Enter zipCode: ", true, String::toString);
        return new Address(street, zipCode);
    }

    public static Address fillAddressFromFile(Scanner scanner) throws FiledIncorrect {
        System.out.println("Entering the Address object");
        String street = (String) ConsoleReader.conditionalRead(scanner, "", true, String::toString);
        String zipCode = (String) ConsoleReader.conditionalRead(scanner, "", true, String::toString);
        return new Address(street, zipCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address chapter = (Address) o;
        return Objects.equals(street, chapter.street) && Objects.equals(zipCode, chapter.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, zipCode);
    }

    @Override
    public String toString() {
        return
                "\n\t\tstreet: " + street +
                        "\n\t\tzipcode: " + zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
