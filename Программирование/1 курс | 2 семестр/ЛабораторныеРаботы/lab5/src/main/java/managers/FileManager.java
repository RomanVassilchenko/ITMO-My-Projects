package managers;

import collections.Address;
import collections.Coordinates;
import collections.Organization;
import collections.OrganizationType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * The FileManager class is a class that manages the files that are created by the program.
 */
public class FileManager {
    private String filename;
    private final XStream xstream;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FileManager(String filename) {
        this.filename = filename;
        xstream = new XStream();

        xstream.alias("address", Address.class);
        xstream.alias("coordinates", Coordinates.class);
        xstream.alias("organization", Organization.class);
        xstream.alias("organizationType", OrganizationType.class);
        xstream.alias("organizations", CollectionManager.class);
        xstream.addImplicitCollection(CollectionManager.class, "organizationCollection");

        xstream.setMode(XStream.NO_REFERENCES);
        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xstream.allowTypeHierarchy(List.class);
        xstream.allowTypeHierarchy(String.class);
        xstream.ignoreUnknownElements();
    }

    
    /**
     * Write a collection of objects to a file
     * 
     * @param collection the collection to be written to the file
     */
    public void writeCollection(Stack<Organization> collection) {
        if (!filename.equals("")) {
            try (FileWriter collectionFileWriter = new FileWriter(filename)) {

                String xml = xstream.toXML(new ArrayList<>(collection));
                collectionFileWriter.write(xml);

                Console.printLn("Collection was successfully added to the file!");
            } catch (IOException exception) {
                Console.printError("File is a directory or can't be opened!");
            }
        } else Console.printError("Filename is wrong or corrupted!");
    }

    
    /**
     * Reads a collection from a file and returns it as a stack
     * 
     * @return A stack collection of Organization
     */
    public Stack<Organization> readCollection() {
        if (!filename.equals("")) {
            try (Scanner collectionFileScanner = new Scanner(new File(filename))) {

                xstream.setMode(XStream.NO_REFERENCES);
                xstream.addPermission(NoTypePermission.NONE);
                xstream.addPermission(NullPermission.NULL);
                xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
                xstream.allowTypeHierarchy(List.class);
                xstream.allowTypeHierarchy(String.class);
                xstream.ignoreUnknownElements();
                xstream.allowTypes(new Class[] {java.util.ArrayList.class, Organization.class});
                StringBuilder xml = new StringBuilder();
                while(collectionFileScanner.hasNext()){
                    xml.append(collectionFileScanner.nextLine());
                }
                List<Organization> list = (List<Organization>) xstream.fromXML(xml.toString());
                Stack<Organization> organizationStack = new Stack<>();
                organizationStack.addAll(list);
                Console.printLn("Collection was read successfully!");
                return organizationStack;
            } catch (FileNotFoundException exception) {
                Console.printError("File was not found!");
            } catch (NoSuchElementException exception) {
                Console.printError("File is empty!");
            } catch ( NullPointerException exception) {
                Console.printError("Can't find a collection in file!");
            } catch (IllegalStateException exception) {
                Console.printError("Unexpected error!");
                System.exit(0);
            }
        } else Console.printError("Filename is wrong or corrupted!");
        return new Stack<>();
    }

    /**
     * This function returns a string that describes the class
     * 
     * @return The string "FileManager (class for working with files)"
     */
    @Override
    public String toString() {
        return "FileManager (class for working with files)";
    }
}
