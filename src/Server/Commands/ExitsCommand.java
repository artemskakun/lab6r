package Server.Commands;

import Lib.Exceptions.WrongNumberOfElementsException;
import Server.Program.CollectionManager;
import Server.ResponseOut;

public class ExitsCommand extends AbstractCommand {
    CollectionManager collectionManager;
    public ExitsCommand(CollectionManager collectionManager) {
        super("exits", "завершить программу (без сохранения в файл)");
        this.collectionManager = collectionManager;
    }

    /**
     * Start execute command.
     *
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (!arg.isEmpty() || o != null)
                throw new WrongNumberOfElementsException("У данной команды не должно быть аргументов!");
            SaveCommand saveCommand = new SaveCommand(collectionManager);
            saveCommand.startExecute("", "");
            System.exit(0);
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}