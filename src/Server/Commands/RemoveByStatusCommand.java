package Server.Commands;

import Lib.Data.Status;
import Lib.Exceptions.EmptyCollectionException;
import Lib.Exceptions.WrongNumberOfElementsException;
import Server.Program.CollectionManager;
import Server.ResponseOut;

public class RemoveByStatusCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public RemoveByStatusCommand(CollectionManager collectionManager) {
        super("remove_all_by_status <status>", "удалить из коллекции все элементы, равные заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (!arg.isEmpty() || o == null) throw new WrongNumberOfElementsException("У данной команды должен быть объектный аргумент!");
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException("Коллекция пуста!");
            Status status = Status.valueOf(arg.toUpperCase());
            collectionManager.removeByStatus(status);
            ResponseOut.println("Работники успешно удалены!");
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println("Использование: '" + getName() + "'");
        } catch (EmptyCollectionException exception) {
            ResponseOut.println("Коллекция пуста!");
        }catch (IllegalArgumentException exception) {
            ResponseOut.println("Позиции нет в списке!");
            ResponseOut.println("Список позиций - " + Status.nameList());
        }
        return false;
    }
}