package commands;

import exceptions.WrongAmountOfElementsException;
import managers.CollectionManager;
import managers.Console;

public class ShuffleCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private boolean isComplete;

    public ShuffleCommand(CollectionManager collectionManager) {
        super("shuffle", "shuffle the elements of the collection randomly");
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
            if(!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.shuffleCollection();
            Console.printLn("Collection was shuffled");
            isComplete = true;

        } catch (WrongAmountOfElementsException e){
            Console.printError("Usage of (" + argument + ") in " + getName());
        }
    }
}
