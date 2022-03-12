package common.interaction;

import common.data.Address;
import common.data.Coordinates;
import common.data.OrganizationType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class for get Organization value.
 */
public class OrganizationRaw implements Serializable {
    private String name;
    private Coordinates coordinates;
    private float annualTurnover;
    private Long employeesCount;
    private OrganizationType type;
    private Address postalAddress;

    public OrganizationRaw(String name, Coordinates coordinates, float annualTurnover, Long employeesCount, OrganizationType type, Address postalAddress) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
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

    @Override
    public String toString() {
        return "OrganizationRaw{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", annualTurnover=" + annualTurnover +
                ", employeesCount=" + employeesCount +
                ", type=" + type +
                ", postalAddress=" + postalAddress +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationRaw that = (OrganizationRaw) o;
        return Float.compare(that.annualTurnover, annualTurnover) == 0 && name.equals(that.name) && coordinates.equals(that.coordinates) && employeesCount.equals(that.employeesCount) && type == that.type && Objects.equals(postalAddress, that.postalAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, annualTurnover, employeesCount, type, postalAddress);
    }
}
