public class Main {
    public static void main(String[] args) {
        AccountManager.getInstance().testAccounts();
        TransactionManager.getInstance().testTransaction();
        GoalManager.getInstance().testGoals();

        Manager.getInstance().run();
    }
}