package Server.Commands;

import Lib.Data.Organization;
import Lib.Data.Worker;
import Lib.Exceptions.IncorrectInputException;
import Lib.Exceptions.WrongNumberOfElementsException;
import Lib.ProductSer;
import Server.Program.CollectionManager;
import Server.ResponseOut;

import java.util.Set;

public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update", " id {element} обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o) {
        try {
            if (arg.isEmpty() || o == null) throw new WrongNumberOfElementsException("ДОлжны быть аргументы");
            Set<Worker> coll = collectionManager.getCollection();
            int argLong = Integer.parseInt(arg);
            Worker prod = collectionManager.getById(argLong);
            if(prod == null) throw new IncorrectInputException("Продукта с таким id нет!");
            ProductSer ps = (ProductSer) o;
            Worker prod2 = new Worker(
                    collectionManager.generateNextId(),
                    ps.getName(),
                    ps.getCoordinates(),
                    ps.getLDT(),
                    ps.getSalary(),
                    ps.getPosition(),
                    ps.getStatus(),
                    ps.getOrganization()
            );
            collectionManager.removeFromCollection(prod);
            collectionManager.addToCollection(prod2);
            ResponseOut.println("Продукт успешно обновлен!");
            return true;
        } catch (WrongNumberOfElementsException | IncorrectInputException exception) {
            ResponseOut.println(exception.getMessage());
        } catch (NumberFormatException e){
            ResponseOut.println("Аргумент должен быть числом!");
            return false;
        }
        return false;
    }
}