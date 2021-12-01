package Server.Commands;

import Lib.Data.Organization;
import Lib.Data.Worker;
import Lib.Exceptions.EmptyCollectionException;
import Lib.Exceptions.WrongNumberOfElementsException;
import Lib.ProductSer;
import Server.Program.CollectionManager;
import Server.ResponseOut;


import java.util.Date;
import java.util.Set;

public class AddIfMaxCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public AddIfMaxCommand(CollectionManager collectionManager) {
        super("min_by_manufacturer", "вывести любой объект из коллекции, значение поля manufacturer которого является минимальным");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {

        try{
            if (!arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("У данной команды не должно быть аргументов!");
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        ProductSer ps = (ProductSer) o;
        Worker prod = new Worker(
                collectionManager.generateNextId(),
                ps.getName(),
                ps.getCoordinates(),
                ps.getLDT(),
                ps.getSalary(),
                ps.getPosition(),
                ps.getStatus(),
                ps.getOrganization()
        );
        if (collectionManager.collectionSize() == 0 || prod.compareTo(collectionManager.getLast()) > 0) {
            collectionManager.addToCollection(prod);
            ResponseOut.println("Работник успешно добавлен!");
            return true;
        } else ResponseOut.println("Значение работника меньше, чем значение наибольшего из работников!");


        return true;

    }
}
