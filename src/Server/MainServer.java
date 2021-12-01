package Server;

import Client.AskManager;
import Lib.MainConsole;
import Lib.Request;
import Server.Program.CollectionManager;
import Server.Program.CommandManager;
import Server.Program.Console;
import Server.Program.FileManager;
import Server.Commands.*;


import java.util.Scanner;
import java.util.logging.Logger;

public class MainServer {

    public static Logger logger = Logger.getLogger("ServerLogger");
    public static int port = 3578;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static String envVariable = "LABA";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Должен быть введён порт!");
            System.exit(0);
        }
        port = Integer.parseInt(args[0]);

        Scanner scanner = new Scanner(System.in);

        AskManager askManager = new AskManager(scanner);
        Console console = new Console(scanner, askManager);



        /*System.out.println("gdf");*/
        FileManager fileManager = new FileManager(envVariable);
        /*System.out.println("gdf2");*/
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExecuteScriptCommand(console),
                new ExitCommand(),
                new ExitsCommand(collectionManager),
                new SaveCommand(collectionManager),
                new AddIfMaxCommand(collectionManager),
                new AddIfMinCommand(collectionManager),
                new RemoveLowerCommand(collectionManager),
                new CountByPositionCommand(collectionManager),
                new RemoveByStatusCommand(collectionManager),
                new PrintAscendingCommand(collectionManager)
        );
        console.setCommandManager(commandManager);


        ServerConsole serverConsole = new ServerConsole(scanner);
        RequestIn requestIn = new RequestIn(commandManager);
        Thread threadForReceiveFromTerminal = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Request req = serverConsole.handle(1);
                    MainConsole.println(requestIn.handle(req).getResponseBody());
                }

            }
        };
        Thread startReceiveFromServerTerminal = new Thread(threadForReceiveFromTerminal);
        startReceiveFromServerTerminal.start();

        Server server = new Server(port, CONNECTION_TIMEOUT, requestIn);
        server.run();
    }
}
