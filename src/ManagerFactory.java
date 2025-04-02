import java.util.Scanner;

public class ManagerFactory {
    private static final GoalManager goalManager = new GoalManager();
    private static final AccountManager accountManager = new AccountManager();
    private static final TransactionManager transactionManager = new TransactionManager();

    public static GoalManager getGoalManager() {
        return goalManager;
    }

    public static AccountManager getAccountManager() {
        return accountManager;
    }

    public static TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static Manager getMainManager() {
        return new Manager();
    }
}
