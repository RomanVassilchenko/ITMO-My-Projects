package collections;

import java.time.LocalDateTime;

/**
 * Organization is storing in collection
 */
public class Organization implements Comparable<Organization> {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float annualTurnover; //Значение поля должно быть больше 0
    private Long employeesCount; //Поле м   ожет быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле может быть null
    private Address postalAddress; //Поле может быть null

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
     * get Organization's id
     * @return get id of current organization
     */
    public int getId() {
        return id;
    }

    /**
     * get Organization's name
     * @return get name of current organization
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set new name to current organization
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get Organization's coordinates
     * @return get coordinates of current organization
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @param coordinates set new coordinates to current organization
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * get Organization's creation time
     * @return get creation date of current organization
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate set new creation date to current organization
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * get Organization's annual turnover
     * @return get annual turnover of current organization
     */
    public float getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * @param annualTurnover set new annual turnover to current organization
     */
    public void setAnnualTurnover(float annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    /**
     * get Organization's employees count
     * @return get employees count of current organization
     */
    public Long getEmployeesCount() {
        return employeesCount;
    }

    /**
     * @param employeesCount set new employees count to the current organization
     */
    public void setEmployeesCount(Long employeesCount) {
        this.employeesCount = employeesCount;
    }

    /**
     * get Organization's type
     * @return get type of the current organization
     */
    public OrganizationType getType() {
        return type;
    }

    /**
     * @param type set new type to the current organization
     */
    public void setType(OrganizationType type) {
        this.type = type;
    }

    /**
     * get Organization's postal address
     * @return get postal address of current organization
     */
    public Address getPostalAddress() {
        return postalAddress;
    }

    /**
     * @param postalAddress get new postal address to the current organization
     */
    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
     * compare current and another organization
     * @param o the organization which we want to compare with the current one
     * @return the division of IDs (current and the one we want to compare)
     */
    @Override
    public int compareTo(Organization o) {
        return this.getId() - o.getId();
    }

    /**
     * get beautiful output of organization's data
     * @return return beautiful output of organization's data
     */
    @Override
    public String toString() {
        String result = String.format("Id: %d\nName: %s\nCoordinates: {x: %d, y: %f}\nCreation Time: %s\nAnnual turnover: %f\nEmployees count: %d\nOrganization Type: %s\nAddress: {Street: %s, ZipCode: %s}\n",
                getId(), getName(), getCoordinates().getX(), getCoordinates().getY(), getCreationDate(), getAnnualTurnover(), getEmployeesCount(), getType(), getPostalAddress().getStreet(), getPostalAddress().getZipCode());
        return result;
    }
}