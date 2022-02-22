package collections;
import java.time.LocalDateTime;

/**
 * An organization is a business entity that has a name, coordinates, creation date, annual turnover,
 * employees count, type and postal address
 */
public class Organization implements Comparable<Organization> {
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
     * Get the id of the current object
     * 
     * @return The id of the question.
     */
    public int getId() {
        return id;
    }

    /**
     * It sets the id of the object to the value passed in.
     * 
     * @param id The id of the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * It returns the name of the person.
     * 
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    
    /**
     * It sets the name of the object to the value of the parameter.
     * 
     * @param name The name of the parameter.
     */
    public void setName(String name) {
        this.name = name;
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
     * It sets the coordinates of the object.
     * 
     * @param coordinates The coordinates of the location.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    
    /**
     * Get the creation date of the object
     * 
     * @return The creation date of the question.
     */

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    
    /**
     * It sets the creationDate to the value passed in.
     * 
     * @param creationDate The date and time when the user was created.
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    
    /**
     * The getAnnualTurnover function returns the annual turnover of the company
     * 
     * @return The annual turnover of the company.
     */

    public float getAnnualTurnover() {
        return annualTurnover;
    }

    
    /**
     * It sets the annual turnover of the company.
     * 
     * @param annualTurnover The annual turnover of the company.
     */
    public void setAnnualTurnover(float annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    
    /**
     * Get the number of employees in the company
     * 
     * @return The number of employees in the company.
     */

    public Long getEmployeesCount() {
        return employeesCount;
    }

    
    /**
     * It sets the employeesCount variable to the value passed in.
     * 
     * @param employeesCount The number of employees in the company.
     */
    public void setEmployeesCount(Long employeesCount) {
        this.employeesCount = employeesCount;
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
     * It sets the type of the organization.
     * 
     * @param type The type of organization.
     */
    /**
     * The getPostalAddress() function returns the postal address of the person
     * 
     * @return The postal address of the person.
     */
    public void setType(OrganizationType type) {
        this.type = type;
    }


    public Address getPostalAddress() {
        return postalAddress;
    }

    
    /**
     * It sets the postalAddress field of the class to the value of the parameter.
     * 
     * @param postalAddress The address of the person.
     */
    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
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
     * @return The toString() method returns a string representation of the object.
     */
    @Override
    public String toString() {
        String result = String.format("Id: %d\nName: %s\nCoordinates: {x: %d, y: %f}\nCreation Time: %s\nAnnual turnover: %f\nEmployees count: %d\nOrganization Type: %s\nAddress: {Street: %s, ZipCode: %s}\n",
                getId(), getName(), getCoordinates().getX(), getCoordinates().getY(), getCreationDate(), getAnnualTurnover(), getEmployeesCount(), getType(), getPostalAddress().getStreet(), getPostalAddress().getZipCode());
        return result;
    }
}