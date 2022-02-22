package commands;

/**
 * This class is an abstract class that implements the ICommand interface
 */
public abstract class AbstractCommand implements ICommand{

    private String name;
    private String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }


    /**
     * It returns the name of the person.
     * 
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }


/**
 * It returns the description of the question.
 * 
 * @return The description of the question.
 */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }

    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractCommand other = (AbstractCommand) obj;
        return name.equals(other.name) && description.equals(other.description);
    }
}

