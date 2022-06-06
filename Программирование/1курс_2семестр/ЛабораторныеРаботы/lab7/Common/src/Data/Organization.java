package Data;

import Exceptions.FiledIncorrect;
import Exceptions.WrongOrganizationException;
import Reader.ConsoleReader;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class Organization implements Serializable, Comparable<Organization> {
    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private Float annualTurnOver;
    private Long employeesCount;
    private OrganizationType type;
    private Address address;
    private User owner;

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Organization(int id, String name, Coordinates coordinates, LocalDate creationDate, Float annualTurnOver, Long employeesCount, OrganizationType type, Address address, User owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.annualTurnOver = annualTurnOver;
        this.employeesCount = employeesCount;
        this.type = type;
        this.address = address;
        this.owner = owner;
    }

    public Organization() {
    }

    public void validate() {
        try {
            if (this.id <= 0 ) throw new WrongOrganizationException("ID");
            if (this.name == null || this.name.equals("")) throw new WrongOrganizationException("Name");
            if (this.coordinates == null) throw new WrongOrganizationException("Coordinates");
            if (this.creationDate == null) throw new WrongOrganizationException("CreationDate");
            if (this.annualTurnOver <= 0) throw new WrongOrganizationException("AnnualTurnover");
            if (this.employeesCount != null && employeesCount <= 0) throw new WrongOrganizationException("EmployeesCount");
            if(this.address.getZipCode().length() >= 22) throw new WrongOrganizationException("Address (ZipCode)");
        } catch (WrongOrganizationException e) {
            e.getMessage();
            System.exit(0);
        }
    }

    public static Organization fillOrganization(Scanner scanner) {
        Organization organization = new Organization();
        try {
            System.out.println("Entering an Organization object");
            organization.name = (String) ConsoleReader.conditionalRead(scanner, "Enter name: ", false, String::toString, Objects::nonNull, (m) -> !m.equals(""));
            organization.coordinates = Coordinates.fillCoordinates(scanner);
            organization.creationDate = LocalDate.now();
            organization.annualTurnOver = (Float) ConsoleReader.conditionalRead(scanner, "Enter annual turnover: ", false, Float::parseFloat, (m) -> Float.parseFloat(m) > 0);
            organization.employeesCount = (Long) ConsoleReader.conditionalRead(scanner, "Enter employees count: ", true, Long::parseLong, (m) -> (m == null || Long.parseLong(m) > 0));
            organization.type = OrganizationType.fillOrganizationType(scanner);
            organization.address = Address.fillAddress(scanner);
        } catch (NoSuchElementException e) {
            System.err.println("Well, why-you haven't fully introduced Organization :(");
        } catch (FiledIncorrect filedIncorrect) {
            filedIncorrect.printStackTrace();
        }
        return organization;
    }

    @Override
    public String toString() {
        return  "ID:" + id +
                "\nUSER: "+ owner+
                "\n\tName: " + name  +
                "\n\tCoordinates: " + coordinates +
                "\n\tCreationDate(YYYY-MM-DD): " + creationDate +
                "\n\tAnnualTurnover: " + annualTurnOver +
                "\n\tEmployeesCount: " + employeesCount +
                "\n\tType: " + type +
                "\n\tAddress: " + address
                ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Float getAnnualTurnOver() {
        return annualTurnOver;
    }

    public void setAnnualTurnOver(Float annualTurnOver) {
        this.annualTurnOver = annualTurnOver;
    }

    public Long getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(Long employeesCount) {
        this.employeesCount = employeesCount;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public int compareTo(Organization o) {
        return this.name.compareTo(o.getName());
    }
}
