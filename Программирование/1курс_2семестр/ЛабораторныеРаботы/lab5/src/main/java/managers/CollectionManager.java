package managers;

import collections.Organization;
import collections.OrganizationType;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;


/**
 * The CollectionManager class is a singleton class that manages the collection of all the collections
 * in the application
 */
public class CollectionManager {
    @XStreamImplicit
    private Stack<Organization> organizationCollection;

    private final LocalDateTime creationDate;

    public CollectionManager() {
        organizationCollection = new Stack<>();
        creationDate = LocalDateTime.now();
    }

    /**
     * Get the creation date of the object
     * 
     * @return The creation date of the collection.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
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
     * The setCollection function sets the organizationCollection field to the value of the
     * organizationCollection parameter
     * 
     * @param organizationCollection The collection of organizations that the user is a member of.
     */
    public void setCollection(Stack<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }


    /**
     * Given an id, return the organization with that id
     * 
     * @param id The id of the organization to be retrieved.
     * @return the response of right execution.
     */
    public Organization getById(int id){
        for (Organization organization: organizationCollection) {
            if(organization.getId() == id) return organization;
        }
        return null;
    }

    /**
     * Replace the Organization with the given id with the new value
     * 
     * @param id The id of the organization to be replaced.
     * @param newValue The new value to be set.
     */
    public void replaceById(int id,Organization newValue){
        newValue.setId(id);
        organizationCollection
                .stream()
                .filter(organization -> organization.getId() == id)
                .findFirst()
                .ifPresent(organization -> organizationCollection.set(organizationCollection.indexOf(organization), newValue));
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
     * Remove an organization from the collection if it exists
     * 
     * @param id The id of the organization to remove.
     */
    public void removeByIdFromCollection(int id){
        organizationCollection.stream()
                .filter(organization -> organization.getId() == id)
                .findFirst()
                .ifPresent(this::removeFromCollection);
    }

    /**
     * Remove an element from a collection
     * 
     * @param id The id of the organization to remove.
     */
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
     * Given a collection of Organization objects, return the maximum id value of the collection. 
     * If the collection is empty, return 0
     * 
     * @return The id of the organization that was just added.
     */
    public int generateNewIdForCollection(){
        int id = organizationCollection.stream()
                .mapToInt(Organization::getId)
                .filter(organization -> organization >= 0)
                .max().orElse(0);
        return id + 1;
    }

    /**
     * This function returns a string that contains information about the collection
     * 
     * @return The string "Type - " + organizationCollection.getClass() + "\n" +
     *                 "Creation date - " + getCreationDate() + "\n" +
     *                 "Amount of elements - " + organizationCollection.size();
     */
    public String infoAboutCollection(){
        return "Type - " + organizationCollection.getClass() + "\n" +
                "Creation date - " + getCreationDate() + "\n" +
                "Amount of elements - " + organizationCollection.size();
    }


    /**
     * Get all the organization types in the system
     * 
     * @return A list of all the types of organizations in the system.
     */
    public List<OrganizationType> getAllOrganizationTypes(){
        return organizationCollection.stream()
                .map(Organization::getType)
                .collect(Collectors.toList());
    }

}
