package common.data;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * The Organization class represents an organization
 */
public class Organization implements Comparable<Organization>, Serializable {
    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private float annualTurnover;
    private Long employeesCount;
    private OrganizationType type;
    private Address postalAddress;

    public Organization(){

    }

    public Organization(int id, String name, Coordinates coordinates, LocalDateTime creationDate, float annualTurnover, Long employeesCount, OrganizationType type, Address postalAddress) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    
    
    /**
     * Returns the id of the object
     * 
     * @return The id of the organization.
     */
    public int getId() {
        return id;
    }

    
    /**
     * It sets the id of the object to the value passed in.
     * 
     * @param id The id of the organization.
     */
    public void setId(int id) {
        this.id = id;
    }

    
/**
 * Returns the name of the organization
 * 
 * @return The name of the organization.
 */
    public String getName() {
        return name;
    }


    /**
     * Returns the coordinates of the point
     * 
     * @return The coordinates of the point.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }


    /**
     * Get the creation date of the object
     * 
     * @return The creation date of the organization.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }


/**
 * Returns the annual turnover of the organization
 * 
 * @return The annual turnover of the organization.
 */
    public float getAnnualTurnover() {
        return annualTurnover;
    }


    /**
     * Get the number of employees in the company
     * 
     * @return The number of employees in the organization.
     */
    public Long getEmployeesCount() {
        return employeesCount;
    }


    /**
     * Returns the type of the organization
     * 
     * @return The type of the organization.
     */
    public OrganizationType getType() {
        return type;
    }


    /**
     * The getPostalAddress() function returns the postal address of the organization
     * 
     * @return The postal address of the organization.
     */
    public Address getPostalAddress() {
        return postalAddress;
    }


    /**
     * This method is used to compare two objects
     * 
     * @param o The object to compare to.
     * @return The id of the organization.
     */
    @Override
    public int compareTo(Organization o) {
        return this.getId() - o.getId();
    }

    
    
    /**
     * Prints the organization's information
     * 
     * @return The toString() method returns a string representation of the Organization object.
     */
    @Override
    public String toString() {
        String result = String.format("Id: %d\nName: %s\nCoordinates: {x: %d, y: %f}\nCreation Time: %s\nAnnual turnover: %f\nEmployees count: %d\nOrganization Type: %s\n",
                getId(), getName(), getCoordinates().getX(), getCoordinates().getY(), getCreationDate(), getAnnualTurnover(), getEmployeesCount(), getType());
        if(getPostalAddress() == null) result += "Address: null";
        else result += String.format("Address: {Street: %s, ZipCode: %s}", getPostalAddress().getStreet(), getPostalAddress().getZipCode());
        return result;
    }
}