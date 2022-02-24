package commands;

import managers.CollectionManager;
import managers.FileManager;

public class SaveCommand extends AbstractCommand {
    private boolean isComplete;
    private final FileManager fileManager;
    private final CollectionManager collectionManager;

    public SaveCommand(FileManager fileManager, CollectionManager collectionManager) {
        super("save", "save collection to file");
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void execute(String argument) {
        isComplete = false;
        fileManager.writeCollection(collectionManager.getCollection());
        isComplete = true;
    }
}
