package Server.Commands;

import Lib.Data.Position;
import Lib.Exceptions.WrongNumberOfElementsException;
import Server.Program.CollectionManager;
import Server.ResponseOut;

public class CountByPositionCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public CountByPositionCommand(CollectionManager collectionManager) {
        super("count_by_price", "вывести количество элементов, значение поля price которых равно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("Должен присутствовать только строковый аргумент");
            Position argPos = Position.valueOf(arg);
            ResponseOut.println(collectionManager.positionCountedInfo(argPos));
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}
