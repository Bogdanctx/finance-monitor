import controller.ControllerFactory;
import controller.REST;
import database.Database;

public class Main {
    public static void main(String[] args) {
        ControllerFactory controllers = new ControllerFactory();
        Database.connect();
        Database.setControllers(controllers);
        Database.loadData();

        REST rest = new REST();

        rest.run();
    }
}