package server.utility;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import common.data.Organization;
import common.data.OrganizationType;

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

    private Stack<Organization> organizationCollection;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private CollectionFileManager collectionFileManager;

    public CollectionManager(CollectionFileManager collectionFileManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.collectionFileManager = collectionFileManager;
        loadCollection();
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
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
     * @return Collection content or corresponding string if collection is empty.
     */
    public String showCollection() {
        if (organizationCollection.isEmpty()) return "Collection is empty!";
        return organizationCollection.stream()
                .map(organization -> organization.toString() + "\n").collect(Collectors.joining());
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return organizationCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return organizationCollection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */
    public Organization getFirst() {
        return organizationCollection.stream().findFirst().orElse(null);
    }

    /**
     * @param id ID of the organization.
     * @return A organization by his ID or null if marine isn't found.
     */
    public Organization getById(int id) {
        return organizationCollection.stream().filter(marine -> marine.getId()==id).findFirst().orElse(null);
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
    public void removeByIdFromCollection(long id){
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
     * Generates next ID. It will be (the bigger one + 1).
     *
     * @return Next ID.
     */
    public int generateNextId(){
        if(organizationCollection.isEmpty()) return 1;
        else return organizationCollection.stream()
                .mapToInt(Organization::getId)
                .filter(organization -> organization >= 0)
                .max().orElse(0) + 1;
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
                "Last Init time - " + getLastInitTime() + "\n" +
                "Last Save time - " + getLastSaveTime() + "\n" +
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

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
        collectionFileManager.writeCollection(organizationCollection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        organizationCollection = collectionFileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }



}
