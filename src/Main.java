public class Main {
    public static void main(String[] args) {
        Manager manager = ManagerFactory.getMainManager();

        manager.unitTest();

        manager.run();
    }
}