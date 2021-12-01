package Server.Commands;

import Lib.Exceptions.WrongNumberOfElementsException;
import Server.Program.CollectionManager;
import Server.ResponseOut;

public class PrintAscendingCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public PrintAscendingCommand(CollectionManager collectionManager) {
        super("print_ascending", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    /**
     * Start execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (!arg.isEmpty()) throw new WrongNumberOfElementsException("У данной команды не должно быть аргумента!");
            ResponseOut.println(collectionManager.toString());
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}
