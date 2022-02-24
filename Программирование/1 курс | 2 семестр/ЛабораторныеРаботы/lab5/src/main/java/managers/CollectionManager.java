package managers;

import collections.Organization;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Stack;

/**
 * This class is used to manage a collection of Organization objects
 */
public class CollectionManager {
    @XStreamImplicit
    private Stack<Organization> organizationCollection;

    private LocalDateTime creationDate;

    public CollectionManager() {
        organizationCollection = new Stack<>();
        creationDate = LocalDateTime.now();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
 * This function returns the collection of organizations
 * 
 * @return The Stack of Organization objects.
 */
    public Stack<Organization> getCollection() {
        return organizationCollection;
    }

/**
 * This function sets the collection of organizations
 * 
 * @param organizationCollection The collection of organizations that the user is a member of.
 */
    public void setCollection(Stack<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }

    /**
     * Returns the name of the class of the collection
     * 
     * @return The class name of the collection.
     */
    public String collectionType() {
        return organizationCollection.getClass().getName();
    }

    /**
     * Returns the number of elements in the collection
     * 
     * @return The size of the collection.
     */
    public int collectionSize() {
        return organizationCollection.size();
    }

    /**
     * Get the first element in the collection
     * 
     * @return The first element in the collection.
     */
    public Organization getFirst() {
        if (organizationCollection.isEmpty()) return null;
        return organizationCollection.firstElement();
    }

    /**
     * Returns the last Organization in the collection
     * 
     * @return The last element of the collection.
     */
    public Organization getLast() {
        if (organizationCollection.isEmpty()) return null;
        return organizationCollection.lastElement();
    }

    /**
     * Given an id, return the organization with that id
     * 
     * @param id The id of the organization to be retrieved.
     * @return Nothing.
     */
    public Organization getById(int id){
        for (Organization organization: organizationCollection) {
            if(organization.getId() == id) return organization;
        }
        return null;
    }

    public void replaceById(int id,Organization newValue){
        for(Organization organization: organizationCollection){
            if(organization.getId() == id){
                organization.setName(newValue.getName());
                organization.setAnnualTurnover(newValue.getAnnualTurnover());
                organization.setCoordinates(newValue.getCoordinates());
                organization.setCreationDate(newValue.getCreationDate());
                organization.setEmployeesCount(newValue.getEmployeesCount());
                organization.setPostalAddress(newValue.getPostalAddress());
                organization.setType(newValue.getType());
            }
        }
    }

    /**
     * Given an organization, return the organization if it exists in the collection, otherwise return
     * null
     * 
     * @param organizationToFind The organization that you want to find.
     * @return The organization that was found.
     */
    public Organization getByValue(Organization organizationToFind){
        for(Organization organization: organizationCollection){
            if(organization.equals(organizationToFind)) return organization;
        }
        return null;
    }

    /**
     * Add an organization to the collection of organizations
     * 
     * @param organization The organization to add to the collection.
     */
    public void addToCollection(Organization organization){
        organizationCollection.add(organization);
    }

    /**
     * Remove an organization from the collection
     * 
     * @param organization The organization to be removed from the collection.
     */
    public void removeFromCollection(Organization organization){
        organizationCollection.remove(organization);
    }

    /**
     * Remove an organization from the collection if it's id matches the id passed in
     * 
     * @param id The id of the organization to remove.
     */
    public void removeByIdFromCollection(int id){
        for(Organization organization: organizationCollection){
            if(organization.getId() == id) removeFromCollection(organization);
        }
    }

    public void removeAtInCollection(int id){
        organizationCollection.remove(id);
    }

    /**
     * Clear the collection of all the organizations
     */
    public void clearCollection(){
        organizationCollection.clear();
    }

    /**
     * This function shuffles the collection of organizations
     */
    public void shuffleCollection(){
        Collections.shuffle(organizationCollection);
    }

    /**
     * This function generates a new id for a collection of objects
     * 
     * @return The id for the new organization in the collection.
     */
    public int generateNewIdForCollection(){
        int id = 0;
        for(Organization organization : organizationCollection){
            if(organization.getId() > id) id = organization.getId();
        }
        return id;
    }

    public String infoAboutCollection(){
        return "Type - " + organizationCollection.getClass() + "\n" +
                "Creation date - " + getCreationDate() + "\n" +
                "Amount of elements - " + organizationCollection.size();
    }


}
