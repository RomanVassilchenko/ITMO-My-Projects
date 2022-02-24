package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

public class RemoveAtCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private boolean isComplete;

    public RemoveAtCommand(CollectionManager collectionManager) {
        super("remove_at index", "remove the element at the given position in the collection (index)");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void execute(String argument) {
        isComplete = false;
        try{
            if(argument.isEmpty()) throw new WrongAmountOfElementsException();
            int id = Integer.parseInt(argument);
            collectionManager.removeAtInCollection(id);
            Console.printLn("Organization was removed successfully");
            isComplete = true;
        }catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        } catch (NumberFormatException e) {
            Console.printError("The id have to be an Integer value");
        }
    }
}
