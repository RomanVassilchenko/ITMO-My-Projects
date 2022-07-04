package collections;



/**
 * An address is a street and a zip code
 */
public class Address {
    private final String street;
    private final String zipCode;

    public Address(String street, String zipCode){
        this.street = street;
        this.zipCode = zipCode;
    }


    /**
     * It returns the street name of the address.
     * 
     * @return The street variable.
     */
    public String getStreet() {
        return street;
    }

    /**
     * The getZipCode function returns the value of the zipCode variable
     * 
     * @return The zip code of the address.
     */
    public String getZipCode() {
        return zipCode;
    }
}