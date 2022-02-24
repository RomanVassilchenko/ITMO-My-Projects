package managers;

import collections.Address;
import collections.Coordinates;
import collections.Organization;
import collections.OrganizationType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import java.io.*;
import java.util.*;

/**
 * Operates the file for saving/loading collection.
 */
public class FileManager {
    private final String filename;
    private final XStream xstream;

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
     * Writes collection to a file.
     * @param collection Collection to write.
     */
    public void writeCollection(Collection<?> collection) {
        if (!filename.equals("")) {
            try (FileWriter collectionFileWriter = new FileWriter(new File(filename))) {

                String xml = xstream.toXML(new ArrayList(collection));
                collectionFileWriter.write(xml);

                Console.printLn("Collection was successfully added to the file!");
            } catch (IOException exception) {
                Console.printError("File is a directory or can't be opened!");
            }
        } else Console.printError("Filename is wrong or corrupted!");
    }

    /**
     * Reads collection from a file.
     * @return Read collection.
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

    @Override
    public String toString() {
        return "FileManager (class for working with files)";
    }
}
