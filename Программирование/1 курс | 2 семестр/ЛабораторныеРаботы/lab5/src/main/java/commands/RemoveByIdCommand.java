package commands;

import exceptions.MustBeNotEmptyException;
import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private boolean isComplete;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id id", "remove element from collection by its id");
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
            if(collectionManager.getById(id) == null) throw new MustBeNotEmptyException();
            collectionManager.removeByIdFromCollection(id);
            Console.printLn("Organization was removed successfully");
            isComplete = true;

        } catch (WrongAmountOfElementsException e){
            Console.printError("No arguments in " + getName());
        } catch (NumberFormatException e) {
            Console.printError("The id have to be an Integer value");
        } catch (MustBeNotEmptyException e) {
            Console.printError("There is no organization with this id");
        }
    }
}
