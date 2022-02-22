package collections;


/**
 * The Address class is a class that represents an address
 */
public class Address {
    private String street;
    private String zipCode;

    public Address(String street, String zipCode){
        this.street = street;
        this.zipCode = zipCode;
    }


    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipCode(String zipCode) {
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