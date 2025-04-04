public class Main {
    public static void main(String[] args) {
        Manager manager = ManagerFactory.getMainManager();
        Service.loadDataFromDatabase();

        manager.run();
    }
}