package collections;

/**
 * Address with street and zipcode
 */
public class Address {
    private String street; //Поле может быть null
    private String zipCode; //Длина строки не должна быть больше 22, Поле может быть null

    public Address(String street, String zipCode){
        this.street = street;
        this.zipCode = zipCode;
    }

    /**
     * get Organization's street
     * @return Street of the Address
     */
    public String getStreet() {
        return street;
    }

    /**
     * get Organization's zipcode
     * @return ZipCode of the Address
     */
    public String getZipCode() {
        return zipCode;
    }
}